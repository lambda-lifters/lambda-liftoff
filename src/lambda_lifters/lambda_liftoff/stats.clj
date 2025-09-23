(ns lambda-lifters.lambda-liftoff.stats)

(defn mean [xs] (/ (reduce + xs) (count xs)))

(defn sqr [x] (* x x))

(defn sd [xs]
  (let [n (count xs)
        m (mean xs)]
    (Math/sqrt (/ (reduce + (map (comp sqr #(- % m)) xs)) (dec n)))))

(defn shannon-index [vs]
  (let [population (reduce + vs)]
    (- (reduce + (map #(let [p_i (/ % population)]
                         (* p_i (Math/log p_i)))
                      vs)))))
