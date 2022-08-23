(ns exercism-track-downloader.core
  "Application to download all exercises from Exercism for any track(s)."
  {:author "Prashanth Kumar"}
  (:require [clojure.string :as str]
            [clojure.set :as cset]
            [hickory.core :as html]
            [hickory.select :as html.pick]
            [clj-http.client :as http]
            [jsonista.core :as json]
            [clojure.pprint :as pp]
            [clojure.java.shell :as sh]
            [cli-matic.core :as cli]
            [jansi-clj.core :as jansi])
  (:gen-class))

(def ^:dynamic *tracks-url* "https://exercism.io/tracks")

(defn get-tracks []
  "Returns hash-map of programming languages and corresponding URL abbreviations (slugs)."
  {:added "1.0"}
  (->> *tracks-url*
       slurp
       (re-seq #"<a href=\"/tracks/.*<\/a>")
       (map #(str/split % #"<|>|=|\"|/tracks/"))
       (mapcat #(remove #{"a href" "/a" ""} %))
       (apply hash-map)))

(defn map-kv [f coll]
  "Applies function to values of hash-map collection."
  {:private true
   :added "1.0"}
  (reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))

(def ^:dynamic *tracks* (map-kv str/lower-case (get-tracks)))

(defn track? [track]
  "Returns true if track is available in Exercism. The check for track is case-insensitive."
  {:added "1.0"}
  (->> *tracks* (into #{} cat) (#(contains? % (str/lower-case track)))))

(defn validate-tracks [tracks]
  "Returns a map of valid and invalid tracks. A track is valid if it is available in Exercism."
  {:added "1.0"}
  (-> (group-by track? tracks) (cset/rename-keys {true :valid false :invalid})))

(defn get-track-slug [track]
  "Gets URL name for track"
  {:added "1.0"}
  (let [track-lower (str/lower-case track)]
   (->> *tracks*
        (keep (fn [[k v]] (when (or (= k track-lower) (= v track-lower)) k)))
        first)))

(defn make-exercise-url [track]
  "Returns the exercises URL for a track."
  {:added "1.0"}
  (->> track get-track-slug (format "https://exercism.org/tracks/%s/exercises")))

(defn parse-exercise-html [track]
  "Get exercises html page for track and parses to hickory format."
  {:private true
   :added "1.0"}
  (-> (-> track make-exercise-url http/get) :body html/parse html/as-hickory))

(defn select-exercises-div [parsed-exercise-url]
  "Selects div element from hickory format of exercises html page."
  {:private true
   :added "1.0"}
  (->> parsed-exercise-url
       (html.pick/select (html.pick/child (html.pick/class "exercises")
                                          (html.pick/tag :div)))))

(defn get-exercises [track]
  "Returns JSON data for track from exercises html page."
  {:added "1.0"}
  (->> track
       parse-exercise-html
       select-exercises-div
       first
       :attrs :data-react-data
       (#(json/read-value % json/keyword-keys-object-mapper))
       :request :options :initial_data :exercises))

(defn get-exercises-slug [track]
  "Retrieves the :slug key from exercises for a given track."
  {:added "1.0"}
  (->> track get-exercises (map :slug)))

;;==============================================================================
;; CLI
;;==============================================================================

(def ^:dynamic *exercism-tracks* (get-tracks))

(defn rx-matches [regex string]
  "Identical to re-matches but takes regex as string."
  {:added "1.0"}
  (re-matches (re-pattern regex) string))

#_(keep (partial rx-matches "^c.*") (keys *exercism-tracks*))

(defmulti grep-tracks
  "Multimethod to keep tracks matching a regular expression.
  Dispatch is based on class of `tracks`."
  {:added "1.0"}
  (fn [regex tracks] [(class regex) (class tracks)]))

(defmethod grep-tracks [String clojure.lang.ISeq]
  [regex tracks]
  (keep (partial rx-matches regex) tracks))

#_(grep-tracks "^c.*" (keys *exercism-tracks*))

(defmethod grep-tracks [String clojure.lang.PersistentHashMap]
  [regex tracks]
  (keep (fn [[k v]] (when (or (rx-matches regex k) (rx-matches regex v))
                     (hash-map k v)))
        tracks))

#_(grep-tracks ".*L$" *exercism-tracks*)

(defn tracks->table [tracks]
 "Table of slug and language for tracks."
 {:added "1.0"}
 (as-> tracks it
       (into (sorted-map) it)
       (mapv #(hash-map :slug %1 :lang %2) (keys it) (vals it))
       (with-out-str (pp/print-table [:slug :lang] it))))

(defn tracks-outstr [{:keys [list slugs langs search]}]
  "tracks option for CLI."
  {:added "1.0"}
  (let [retrieve-msg (-> ["\nRetrieving tracks from " *tracks-url* "\n"] str/join format)
        make-outstr (fn [x] (str/join "\n" [retrieve-msg x]))]
       (cond
         (true? list) (->> *exercism-tracks* tracks->table make-outstr)
         (true? slugs) (->> *exercism-tracks* keys sort (str/join "\n") make-outstr)
         (true? langs) (->> *exercism-tracks* vals sort (str/join "\n") make-outstr)
         (some? search) (->> *exercism-tracks*
                             (grep-tracks search)
                             tracks->table
                             make-outstr))))

(def cli-opt-tracks tracks-outstr)

(defn exercises->table [track]
  "Formatted-table with exercises, difficulty and type columns for a track."
  {:added "1.0"}
  (-> '[]
     (conj [(str/join " " ["=========" (get-track-slug track) "========="])])
     (conj [(->> track
              get-exercises
              (map #(select-keys % [:title :difficulty :type]))
              (#(with-out-str (pp/print-table %))))])
     flatten
     (#(str/join "\n" %))))

#_(print-exercises-table "Clojure")

(defn exercises-outstr [tracks]
  "exercises --list option for CLI."
  {:added "1.0"}
  (let [{:keys [valid invalid]} (validate-tracks tracks)
        invalid-msg "\n[@|red,bold %s|@] not found. See tracks for available options.\n\n"]
     (->> (cond-> '[]
           (some? invalid) (conj [(-> (jansi/renderf invalid-msg (str/join ", " invalid)))])
           (some? valid) (conj [(->> (map exercises->table valid) (str/join "\n"))]))
         flatten
         str/join)))

#_(print-exercises ["Clojure" "awk"])

(defn generate-exercise-download-commands [track]
  "Generate command for input to sh."
  {:added "1.0"}
  (let [exercises-slug (get-exercises-slug track)
        track-slug (->> track get-track-slug (format "--track=%s"))]
      (map #(vector "exercism" "download" track-slug (str/join ["--exercise=" %]))
           exercises-slug)))

#_(generate-exercise-download-commands "PL/SQL")

(defn download-track [track]
  "Download exercises for single track"
  {:added "1.0"}
  (let [commands (generate-exercise-download-commands track)
        track-prnstr (->> track get-track-slug (jansi/renderf "@|yellow,bold %s|@"))]
    (->> ["\n" "=========" track-prnstr "========="] (str/join " ") println)
    (doseq [cmd commands
            :let [exercise-slug (-> cmd (subvec 3) first (str/split #"=") last)]
            :let [result (apply sh/sh cmd)]]
      (if (zero? (:exit result))
       (println (jansi/renderf "Downloaded exercise @|green,bold %s|@" exercise-slug))
       (println (jansi/renderf "You have not unlocked exercise @|red,bold %s|@" exercise-slug))))))

#_(download-track "plsql")

(defn download-tracks [tracks]
  "Download exercises for multiple tracks"
  {:added "1.0"}
  (->> tracks
       (map get-track-slug)
       distinct
       (map download-track)))

#_(download-tracks ["plsql" "PL/SQL"])

(defn cli-opt-exercises [{:keys [list download]}]
  "exercises option for CLI"
  {:added "1.0"}
  (cond
    (some? list) (-> list exercises-outstr println)
    (some? download) (-> download download-tracks str/join println)))

(def cli-config
  "Configuration for `run-cmd`"
  {:app         {:command     "exercism-track-downloader"
                 :description "Application to download all exercises from Exercism for any track(s)."
                 :version     "1.0"}
   :global-opts []
   :commands    [{:command     "tracks"
                  :description "List available tracks on Exercism"
                  :opts        [{:option "list" :short "l"
                                 :as "List both track slugs and language mappings"
                                 :type :with-flag}
                                {:option "slugs" :short "s"
                                 :as "List track slugs only"
                                 :type :with-flag}
                                {:option "langs" :short "g"
                                 :as "List track languages only"
                                 :type :with-flag}
                                {:option "search" :short "r"
                                 :as "Search available track listings using regex"
                                 :type :string}]
                  :runs        (comp println cli-opt-tracks)
                  :examples    ["exercism-track-downloader tracks --list"
                                "exercism-track-downloader tracks --slugs"
                                "exercism-track-downloader tracks --langs"
                                "exercism-track-downloader tracks --search \".*L$\""]}
                 {:command     "exercises"
                  :description "List and download exercises for each track"
                  :opts        [{:option "list" :short "l"
                                 :as "List exercises for target tracks"
                                 :type :string
                                 :multiple true}
                                {:option "download" :short "d"
                                 :as "Download exercises for target tracks"
                                 :type :string
                                 :multiple true}]
                  :runs        cli-opt-exercises
                  :examples    ["exercism-track-downloader exercises --list plsql -l clojure"
                                "exercism-track-downloader exercises --download wasm -d awk"]}]})

(defn -main
   [& args]
   (cli/run-cmd args cli-config))
