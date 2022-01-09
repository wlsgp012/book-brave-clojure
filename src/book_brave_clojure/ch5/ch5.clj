(ns book-brave-clojure.ch5.ch5
  (:require [clojure.string :as s]))

(defn sum
  ([vals] (sum vals 0))
  ([vals result]
   (if (empty? vals)
     result
     (recur (rest vals) (+ (first vals) result)))))

(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My boa constrictor is so sassy lol!   ")

;; comp
((comp inc *) 2 3)

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-strength (comp :strength :attributes))

(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn sleepy-identity
  [x]
  (Thread/sleep 1000)
  x)

(def memo-sleepy-identity (memoize sleepy-identity))
