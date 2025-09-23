(ns lambda-lifters.lambda-liftoff.trie-test
  (:require [clojure.test :refer :all]
            [hashp.preload]
            [lambda-lifters.lambda-liftoff.trie :as trie]))

(deftest value-from-wikipedia
  (is (=
        {:ids  {"a" 0, "i" 2, "tea" 4, "ted" 5, "ten" 6, "to" 1},
         :trie {\a {:id 0, :prefix "a"},
                \i {:id 2, :prefix "i"},
                \t {:prefix "t",
                    :tail   {\e {:prefix "te",
                                 :tail   {\a {:id 4, :prefix "tea"},
                                          \d {:id 5, :prefix "ted"},
                                          \n {:id 6, :prefix "ten"}}},
                             \o {:id 1, :prefix "to"}}}}}
        (trie/strings->trie ["a" "to" "i" "ten" "tea" "ted" "ten"]))))