(defproject csvmerge2flare "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-yaml "0.4.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [org.clojure/data.csv "0.1.3"]
                 [semantic-csv "0.1.0"]
                 [cheshire "5.5.0"]]
  :main csvmerge2flare.cli
  :uberjar-name "csvmerge2flare.jar"
  :profiles {
             :uberjar {:aot :all}
             :dev     {:dependencies [[midje "1.8.3"]]}
             }
  :plugins [[lein-midje "3.2"]]
  )
