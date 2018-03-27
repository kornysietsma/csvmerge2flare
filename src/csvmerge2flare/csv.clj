(ns csvmerge2flare.csv
  (:require [csvmerge2flare.flare :as flare]
            [clojure-csv.core :as csv]
            [semantic-csv.core :as sc]
            [clojure.java.io :as io]
            [clojure.walk :refer [keywordize-keys]]
            [cheshire.core :as cheshire]
            [clojure.pprint])
  (:import (java.io Reader Writer)))

  (defn convert "convert parsed csv into Slurp format with a given sub-namespace"
    [input category field]
    (for [row input]
      (let [filename (field row)
            data (dissoc row field)]
        {:filename filename
         category     (keywordize-keys data)})))

  (defn convert-csv-to-json "convert input yaml data to output json data"
    [category ^Reader in-file ^Writer out-file]
    (-> in-file
        csv/parse-csv
        sc/process
        (convert category)
        (cheshire/generate-stream out-file {:pretty true})))

(defn- normalize "strip ./ from unix-style paths"
  ; note - you could do this with Java 1.7 Path/normalize
  ; but I'd prefer to stay friendly for old Java versions - and this is very simple.
  ; TODO: what happens in Windows??? Should be safe, but might be pointless.
  [path]
  (if (clojure.string/starts-with? path "./")
    (subs path 2)
    path))

(defn merge-csv-with-json
  [^Reader base-file ^Reader in-file ^Writer out-file root-path category field]
  (let [base-data (cheshire/parse-stream base-file true)]
  (as-> in-file x
      (csv/parse-csv x)
      (sc/process x)
      (convert x category field)
      (flare/combine base-data x :update root-path)
      (cheshire/generate-stream x out-file {:pretty true})
      )
   ))
