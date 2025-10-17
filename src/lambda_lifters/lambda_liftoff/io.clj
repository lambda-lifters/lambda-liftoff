(ns lambda-lifters.lambda-liftoff.io
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log])
  (:import (java.io PushbackReader)
           (java.nio.file Path)))

(defn load-map-from-resource
  "Loads a map from a resource that can be read using make-reader.
  Throws and exception if the resource does not contain edn, or that edn is not a map."
  [resource resource-name]
  (try
    (let [config (with-open [rdr (PushbackReader. (io/reader resource))] (edn/read rdr))]
      (when-not (map? config) (throw (ex-info (str resource-name " is not a map") {:parsed-config config})))
      config)
    (catch Exception e
      (log/error "Error reading config file" e)
      (throw e))))

(defn copy-file
  "Copy a file from source to destination, ensuring destination directory exists"
  [src dest ]
  (log/debug "copy " src " → " (.toString dest))
  (io/make-parents dest)
  (io/copy (io/file src)
           (io/file dest)))

(defn ensure-directory [dir]
  (log/debug "ensure directory" dir)
  (io/make-parents (io/file dir "dummy"))
  (.delete (io/file dir "dummy")))

(defn resource-path [r]
  (Path/of (.toURI (io/resource r))))

