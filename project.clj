(defproject csvmerge2flare "0.1.0"
  :description "utility to merge csv files into a d3 flare data structure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
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
