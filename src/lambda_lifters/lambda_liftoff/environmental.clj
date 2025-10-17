(ns lambda-lifters.lambda-liftoff.environmental
  (:require [clojure.java.shell :as shell]
            [clojure.string :as str]))

(defn get-git-user-name
  "Get the user.name from git config, or fallback to system user"
  []
  (try
    (let [result (shell/sh "git" "config" "user.name")
          username (str/trim (:out result))]
      (if (str/blank? username)
        (System/getProperty "user.name" "Anonymous")
        username))
    (catch Exception _
      (System/getProperty "user.name" "Anonymous"))))
