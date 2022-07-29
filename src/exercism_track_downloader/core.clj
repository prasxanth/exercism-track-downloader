(ns ^{:doc "Application to download all exercises from Exercism for any track(s)."
      :author "Prashanth Kumar"}
    exercism-track-downloader.core
  (:require [clojure.string :as str]
            [clojure.set :as cset]
            [hickory.core :as html]
            [hickory.select :as html.pick]
            [clj-http.client :as http]
            [jsonista.core :as json]
            [clojure.pprint :as pp]
            [clojure.java.shell :as sh]
            [cli-matic.core :as cli])
  (:gen-class))

(def tracks-url (atom "https://exercism.io/tracks"))

(defn ^{:doc "Returns hash-map of programming languages and corresponding URL abbreviations (slugs)."
        :added "1.0"}
  get-tracks []
  (->> @tracks-url
      slurp
      (re-seq #"<a href=\"/tracks/.*<\/a>")
      (map #(str/split % #"<|>|=|\"|/tracks/"))
      (mapcat #(remove #{"a href" "/a" ""} %))
      (apply hash-map)))

(defn ^{:doc "Applies function to values of hash-map collection."
        :private true
        :added "1.0"}
  map-kv [f coll]
  (reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))

(def tracks (->> (get-tracks) (map-kv str/lower-case) atom))

(defn ^{:doc "Returns true if track is available in Exercism. The check for track is case-insensitive."
        :added "1.0"}
  track? [track]
  (->> @tracks (into #{} cat) (#(contains? % (str/lower-case track)))))

(defn ^{:doc "Returns a map of valid and invalid tracks. A track is valid if it is available in Exercism."
        :added "1.0"}
  validate-tracks [tracks]
  (-> (group-by track? tracks) (cset/rename-keys {true :valid false :invalid})))

(defn ^{:doc "Gets URL name for track"
        :added "1.0"}
  get-track-slug [track]
  (let [track-lower (str/lower-case track)]
   (->> @tracks
        (keep (fn [[k v]] (when (or (= k track-lower) (= v track-lower)) k)))
        first)))

(defn ^{:doc "Returns the exercises URL for a track."
        :added "1.0"}
  make-exercise-url [track]
  (->> track get-track-slug (format "https://exercism.org/tracks/%s/exercises")))

(defn ^{:doc "Get exercises html page for track and parses to hickory format."
        :private true
        :added "1.0"}
  parse-exercise-html [track]
  (-> (-> track make-exercise-url http/get) :body html/parse html/as-hickory))

(defn ^{:doc "Selects div element from hickory format of exercises html page."
        :private true
        :added "1.0"}
  select-exercises-div [parsed-exercise-url]
  (->> parsed-exercise-url
       (html.pick/select (html.pick/child (html.pick/class "exercises")
                                          (html.pick/tag :div)))))

(defn ^{:doc "Returns JSON data for track from exercises html page."
        :added "1.0"}
  get-exercises [track]
  (->> track
       parse-exercise-html
       select-exercises-div
       first
       :attrs :data-react-data
       (#(json/read-value % json/keyword-keys-object-mapper))
       :request :options :initial_data :exercises))

(defn ^{:doc "Retrieves the :slug key from exercises for a given track."
        :added "1.0"}
  get-exercises-slug [track]
  (->> track get-exercises (map :slug)))

;;==============================================================================
;; CLI
;;==============================================================================

(def exercism-tracks (atom (get-tracks)))

(defn ^{:doc "Identical to re-matches but takes regex as string."
        :added "1.0"}
  rx-matches [regex string]
  (re-matches (re-pattern regex) string))

#_(keep (partial rx-matches "^c.*") (keys @exercism-tracks))

(defmulti ^{:doc "Multimethod to keep tracks matching a regular expression. Dispatch is based on class of `tracks`."
            :added "1.0"}
  grep-tracks (fn [regex tracks] [(class regex) (class tracks)]))

(defmethod grep-tracks [String clojure.lang.ISeq]
  [regex tracks]
  (keep (partial rx-matches regex) tracks))

#_(grep-tracks "^c.*" (keys @exercism-tracks))

(defmethod grep-tracks [String clojure.lang.PersistentHashMap]
  [regex tracks]
  (keep (fn [[k v]] (when (or (rx-matches regex k) (rx-matches regex v))
                     (hash-map k v)))
        tracks))

#_(grep-tracks ".*L$" @exercism-tracks)

(defn ^{:doc "Table of slug and language for tracks."
        :added "1.0"}
 tracks->table [tracks]
 (as-> tracks it
       (into (sorted-map) it)
       (mapv #(hash-map :slug %1 :lang %2) (keys it) (vals it))
       (with-out-str (pp/print-table [:slug :lang] it))))

(defn ^{:doc "tracks option for CLI."
        :added "1.0"}
  tracks-outstr [{:keys [list slugs langs search]}]
  (let [retrieve-msg (-> ["\nRetrieving tracks from " @tracks-url "\n"] str/join format)
        make-outstr (fn [x] (str/join "\n" [retrieve-msg x]))]
       (cond
         (true? list) (->> @exercism-tracks tracks->table make-outstr)
         (true? slugs) (->> @exercism-tracks keys sort (str/join "\n") make-outstr)
         (true? langs) (->> @exercism-tracks vals sort (str/join "\n") make-outstr)
         (some? search) (->> @exercism-tracks
                             (grep-tracks search)
                             tracks->table
                             make-outstr))))

(def cli-opt-tracks tracks-outstr)

(defn ^{:doc "Formatted-table with exercises, difficulty and type columns for a track."
        :added "1.0"}
  exercises->table [track]
  (-> '[]
     (conj [(str/join " " ["=========" (get-track-slug track) "========="])])
     (conj [(->> track
              get-exercises
              (map #(select-keys % [:title :difficulty :type]))
              (#(with-out-str (pp/print-table %))))])
     flatten
     (#(str/join "\n" %))))

#_(print-exercises-table "Clojure")

(defn ^{:doc "exercises --list option for CLI."
        :added "1.0"}
  exercises-outstr [tracks]
  (let [{:keys [valid invalid]} (validate-tracks tracks)
        invalid-msg "\n[%s] not found. See tracks for available options.\n\n"]
     (->> (cond-> '[]
           (some? invalid) (conj [(-> (format invalid-msg (str/join ", " invalid)))])
           (some? valid) (conj [(->> (map exercises->table valid) (str/join "\n"))]))
         flatten
         str/join)))

#_(print-exercises ["Clojure" "awk"])

(defn ^{:doc "Generate command for input to sh."
        :added "1.0"}
  generate-exercise-download-commands [track]
  (let [exercises-slug (get-exercises-slug track)
        track-slug (->> track get-track-slug (format "--track=%s"))]
      (map #(vector "exercism" "download" track-slug (str/join ["--exercise=" %]))
           exercises-slug)))

#_(generate-exercise-download-commands "PL/SQL")

(defn ^{:doc "Download exercises for single track"
        :added "1.0"}
  download-track [track]
  (let [commands (generate-exercise-download-commands track)
        track-slug (get-track-slug track)]
    (doseq [cmd commands
            :let [exercise-slug (-> cmd (subvec 3) first (str/split #"=") last)]
            :let [result (apply sh/sh cmd)]]
      (if (zero? (:exit result))
       (printf "\nDownloaded exercise <%s> for track <%s>" exercise-slug track-slug)
       (printf "\nYou have not unlocked exercise <%s> for track <%s>" exercise-slug track-slug)))))

#_(download-track "plsql")

(defn ^{:doc "Download exercises for multiple tracks"
        :added "1.0"}
  download-tracks [tracks]
  (->> tracks
       (map get-track-slug)
       distinct
       (map download-track)))

#_(download-tracks ["plsql" "PL/SQL"])

(defn ^{:doc "exercises option for CLI"
        :added "1.0"}
  cli-opt-exercises [{:keys [list download]}]
  (cond
    (some? list) (-> list exercises-outstr println)
    (some? download) (-> download download-tracks (str/join "\n") println)))

(def cli-config
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
