(defproject csvmerge2flare "1.0.0"
  :description "utility to merge csv files into a d3 flare data structure"
  :url "https://github.com/kornysietsma/csvmerge2flare"
  :license {:name "Apache 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-yaml "0.4.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/data.csv "0.1.4"]
                 [semantic-csv "0.2.0"]
                 [cheshire "5.8.0"]]
  :main csvmerge2flare.cli
  :uberjar-name "csvmerge2flare.jar"
  :profiles {
             :uberjar {:aot :all}
             :dev     {:dependencies [[midje "1.9.1"]]}
             }
  :plugins [[lein-midje "3.2"]]
  )
