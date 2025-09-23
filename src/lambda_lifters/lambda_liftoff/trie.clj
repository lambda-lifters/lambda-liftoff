(ns lambda-lifters.lambda-liftoff.trie
  (:require [lambda-lifters.lambda-liftoff.nils :as nils]))

(defn strings->trie
  "Returns a trie for strs, a sequence of strings.
  Optionally takes an index -- a function from string -> identifier.
  If index is not provided, then one is created based on the order of strs."
  [strs & {:keys [index]}]
  (let [ids (or index (into {} (map-indexed #(vector %2 %1)) strs))]
    (letfn [(inner [ids words path]
              (into {}
                    (map (fn [[k v]]
                           (let [tails (keep next v)
                                 path (conj path k)
                                 prefix (apply str path)]
                             [k {:id     (ids prefix)
                                 :prefix prefix
                                 :tail   (inner ids tails path)}])))
                    (group-by first words)))]
      {:ids ids :trie (nils/remove-empty-values (inner ids strs []))})))
