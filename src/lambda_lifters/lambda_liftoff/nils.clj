(ns lambda-lifters.lambda-liftoff.nils
  "Functions related to nil handling"
  (:require [clojure.walk :as walk]))

(defn remove-nil-values
  "Remove nils from a nested map
  Ref: https://stackoverflow.com/questions/3937661/remove-nil-values-from-a-map"
  [m]
  (let [f (fn [[k v]] (when v [k v]))]
    (walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn remove-empty-values
  "Remove empty -- any sequable?, empty? -- values from a nested map
  Ref: https://stackoverflow.com/questions/3937661/remove-nil-values-from-a-map"
  [m]
  (let [f (fn [[k v]] (when-not (and (seqable? v) (empty? v)) [k v]))]
    (walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))
