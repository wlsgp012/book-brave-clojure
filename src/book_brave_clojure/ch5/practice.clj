(ns book-brave-clojure.ch5.practice.clj)

;; 1
(defn attr
  [key]
  (comp key :attributes))

;; 2
(defn my-comp
  [& fs]
  (fn [& args]
    (let [[fi & re] (reverse fs)]
     (reduce
      (fn [y f] (f y))
      (apply fi args)
      re))))

;; 3
(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (k m) ks v))))

;; 4
(def users [{:name "James" :age 26}  {:name "John" :age 43}])
(update-in users [1 :age] inc)

;; 5
(defn my-update-in
  [m ks f & args]
  "todo")
