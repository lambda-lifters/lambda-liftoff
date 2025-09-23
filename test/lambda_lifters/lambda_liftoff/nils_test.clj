(ns lambda-lifters.lambda-liftoff.nils_test
  (:require [clojure.test :refer :all]
            [hashp.preload]
            [lambda-lifters.lambda-liftoff.nils :as nils]))

(deftest remove-empty-values
  (is (= [1 'a 42] (nils/remove-empty-values [1 'a 42])))
  (is (= [1 'a 42 {} #{} (list) nil ""] (nils/remove-empty-values [1 'a 42 {} #{} (list) nil ""])))
  (is (= {:a 1 :b 'a :c 42 #_#_:d {} #_#_:e #{} #_#_:f (list) #_#_:g nil #_#_:h "" #_#_:i []}
         (nils/remove-empty-values {:a 1 :b 'a :c 42 :d {} :e #{} :f (list) :g nil :h "" :i []}))))
