(ns book-brave-clojure.ch3.practice3)

;;1
(println (str "hi" "fellas"))
(vector 1 2 3)
(list "a" "b" "c")
(hash-map :a 1 :b 2)
(hash-set :a :b)

;;2
(defn add-100-on-random
  [x]
  (+ 100 (rand x)))

;;3
(defn dec-maker
  [x]
  #(+ % (* x -1)))

;;4
(defn mapset
  [f v]
  (set (map f v)))

(defn mapset2
  [f v]
  (reduce #(conj %1 (f %2)) #{} v))

(mapset2 inc [1 1 2 2])
