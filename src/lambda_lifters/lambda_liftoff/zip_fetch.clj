(ns lambda-lifters.lambda-liftoff.zip-fetch
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hashp.preload])
  (:import (java.net URI)
           (java.util.zip ZipEntry ZipInputStream)))

(defn- file-from-zip-entry [in-stream map-file-name zip-entry]
  (let [e-name (ZipEntry/.getName zip-entry)
        is-dir? (str/ends-with? e-name "/")]
    (when-let [output-path (some-> e-name map-file-name io/file)]
      (if is-dir?
        (io/make-parents (io/file output-path "."))
        (do (io/make-parents output-path)                   ; belts and braces
            (io/copy in-stream output-path)))
      output-path)))

(defn fetch-and-unzip
  "Fetches a ZIP file from url and unzips it, selectively renaming files.
  The optional :file-name-mapping is a keep-like function that maps each entry in the ZIP file to a file name on the
  local machine, or nil if the file is not to be extracted.

  Directories' entries will occur before their contents,
  good practice would suggest creating (by `keep`ing) those;
  although files will have their parents made before writing anyway.

  Returns a vector of the files that were extracted."
  [url & {:keys [file-name-mapping] :or {file-name-mapping identity}}]
  (with-open [stream (-> url URI. URI/.toURL .openStream ZipInputStream.)]
    (let [extract-file (partial file-from-zip-entry stream file-name-mapping)
          entries (repeatedly #(ZipInputStream/.getNextEntry stream))]
      (into [] (comp (take-while some?) (keep extract-file)) entries))))

(comment
  ;; map "fontawesome-4.7.0/fonts/xxx to fonts/xxx, ditto with css, ignore all other files
  (defn font-awesome-file-name-mapping [output-dir entry-name]
    (condp #(%1 %2) entry-name
      (partial re-matches #"^.*fonts/(.*)") :>> #(io/file output-dir "fonts" (second %))
      (partial re-matches #"^.*[^s]css/(.*)") :>> #(io/file output-dir "css" (second %))
      nil))
  (let [f (java.io.File. "/tmp/TARGET/public_html/")]
    (fetch-and-unzip "https://fontawesome.com/v4/assets/font-awesome-4.7.0.zip"
                     :file-name-mapping (partial font-awesome-file-name-mapping f))))
