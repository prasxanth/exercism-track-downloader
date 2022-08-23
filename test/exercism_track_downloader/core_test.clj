(ns exercism-track-downloader.core-test
  (:require [clojure.test :refer [deftest is are testing]]
            [exercism-track-downloader.core :refer :all]))

(deftest test-tracks-atom
    (is (= (get *tracks* "clojure") "clojure")
        "Language in capital case")
    (is (= (get *tracks* "common-lisp") "common lisp")
        "Language with two words and space")
    (is (= (get *tracks* "plsql") "pl/sql")
        "Language with backslash between words")
    (is (= (get *tracks* "sml") "standard ml")
        "Language with two words and space, abbreviated in URL form")
    (is (= (get *tracks* "csharp") "c#")
        "Language with special character")
    (is (= (get *tracks* "pharo-smalltalk") "pharo")
        "Language with expanded URL form")
    (is (= (get *tracks* "wasm") "webassembly")
        "Language with abbreviated URL form")
    (is (= (get *tracks* "vbnet") "vb.net")
        "Language in capital case with period inbetween words"))

#_(deftest test-tracks-count
    (is (= (count *tracks*) 61) "Number of tracks available on Exercism"))

(deftest test-get-track-slug
  (is (= (map get-track-slug ["BasH" "AwK"]) '("bash" "awk"))
      "Argument is case insensitive")
  (is (= (map get-track-slug ["Standard ML" "VB.NET" "Pharo" "PL/SQL"])
         '("sml" "vbnet" "pharo-smalltalk" "plsql"))
      "Full language form accepted as argument")
  (is (= (map get-track-slug ["wasm" "common-lisp"]) '("wasm" "common-lisp"))
      "URL form also accepted as argument"))

(deftest test-track?
  (is (true? (track? "AWK")) "Valid language in upper case")
  (is (true? (track? "bASh")) "Valid language in mixed case")
  (is (false? (track? "Standard-ML"))
      "Two word language with dash instead of spaces")
  (is (true? (track? "PL/SQL")) "Valid language with backslash")
  (is (false? (track? "Clojr")) "Invalid language")
  (is (true? (track? "phaRO-smallTalk")) "URL form of language in mixed case")
  (is (true? (track? "sml")) "URL short form of two worded language"))

(deftest test-validate-tracks
  (is (= (validate-tracks ["x86-64 Assembbly" "wasm" "VB.NET" "Pure Script"])
         {:valid ["wasm" "VB.NET"] :invalid ["x86-64 Assembbly" "Pure Script"]})))

#_(make-exercise-url "Common Lisp")
#_(parse-exercise-html "Common Lisp")
#_(->> "Clojure" parse-exercise-html select-exercises-div)

#_(deftest test-get-exercises-count
    (is (= (map (comp count get-exercises) ["Clojure" "AWK" "bash" "wren"])
           '(84 58 89 66))
        "Count of Exercises for 4 languages"))

(defn sample-exercises-slug [^String track ^clojure.lang.PersistentVector indices]
  (->> track get-exercises-slug sort vec (#(map % indices))))

(deftest test-get-exercises-slug
  (is (= (sample-exercises-slug "Clojure" [0 15 40 45 55])
         '("accumulate" "cars-assemble" "log-levels" "minesweeper" "pov"))
      "Sample of exercises in Clojure")
  (is (= (sample-exercises-slug "VB.NET" [2 9 10])
         '("allergies" "crypto-square" "hamming"))
      "Sample of exercises in VB.NET")
  (is (= (sample-exercises-slug "sml" [5 7 11])
         '("bob" "diamond" "hello-world"))
      "Sample of exercises in Standard ML (URL form)")
  (is (= (sample-exercises-slug "plsql" [0 1 2])
         '("binary" "difference-of-squares" "gigasecond"))
      "Sample of exercises in PL/SQL (URL form)"))
