(defproject exercism-track-downloader "1.0"
  :description "Application to download all exercises from Exercism for any track(s)."
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [hickory "0.7.1"]
                 [clj-http "3.12.3"]
                 [metosin/jsonista "0.3.6"]
                 [cli-matic "0.5.3"]]
  :main ^:skip-aot exercism-track-downloader.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
