(ns csvmerge2flare.cli
  (:require
    [csvmerge2flare.csv :as csv]
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.java.io :as io]
    [clojure.string :as string])
  (:gen-class))

  (def cli-options
    [["-b" "--base filename" "select a base flare-format json file for merging - if you don't specify a -b option, the program will try to read flare data from standard input for piping"]
     ["-i" "--input filename" "select an input csv file name"
      :validate [#(.exists (io/file %)) "Must be a valid file"]]
     ["-c" "--category cat" "specify the category for the input data - data will be stored in 'node.data.<category>' in the flare JSON structure.  Defaults to the input filename with extensions stripped - you probably don't want that."]
     ["-f" "--field f" "specify which field in a csv file represents the filename - defaults to 'entity' for code-maat files"
      :default "entity"]

     ["-o" "--output filename" "select an output file name (default is STDOUT)"]
     ["-h" "--help"]])

  (defn usage [options-summary]
    (->> ["Utilities for combining csv-based metrics into a flare format for visualization"
          ""
          "Usage: java -jar csvmerge2flare.jar [options]"
          ""
          "Options:"
          options-summary
          ""
          ""
          "Input files are expected to be csv files, similar to the output of code-maat: "
          ""
          "language,filename,blank,comment,code"
          "Clojure,src/code_sniff/cli.clj,7,1,81"
          "Clojure,src/code_sniff/combine.clj,9,0,57"
          ""
          "Output is a flare JSON file for D3 visualization"
          ""
          "Note you must provide a base JSON flare file, and only files in that file will be updated from the CSV, no new files are added."
          ""]
         (string/join \newline)))


(defn exit [status msg]
  (binding [*out* *err*]
    (println msg))
  (System/exit status))

  (defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

  (defn -main [& args]
    (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
      (cond
        (:help options) (exit 0 (usage summary))
        (not= (count arguments) 0) (exit 1 (usage summary))
        errors (exit 1 (error-msg errors)))
      (let [in-file (io/reader (:input options))
            out-file (if (:output options)
                       (io/writer (:output options))
                       *out*)
            base-file (if (:base options)
                                  (io/reader (:base options))
                                  *in*)
            category (keyword (:category options))
            field (keyword (:field options))]
        (try
          (csv/merge-csv-with-json base-file in-file out-file category field )
          (finally
            (if (:input options)
              (.close in-file))
            (if (:output options)
              (.close out-file)))))))
