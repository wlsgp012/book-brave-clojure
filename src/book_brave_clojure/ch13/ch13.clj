(ns book-brave-clojure.ch13.ch13)

;; mulitmethod
(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people and sweat to the oldies"))

(full-moon-behavior {:were-type :wolf :name "Rachel from next door"})

(full-moon-behavior {:were-type :simmons :name "Andy the baker"})

(defmethod full-moon-behavior nil
  [were-creature]
  (str (:name were-creature) " will stat at home and eat ice cream"))

(full-moon-behavior {:were-type nil :name "Martin the nurse"})

(defmethod full-moon-behavior :default
  [were-creature]
  (str (:name were-creature) " will stay up all night fantasy footballing"))

(full-moon-behavior {:were-type :office-worker :name "Jimmy from sales"})

(defmulti types (fn [x y] [(class x) (class y)]))

(defmethod types [java.lang.String java.lang.String]
  [x y]
  "Two strings")
(types "String 1" "String 2")

;; protocol

(defprotocol Psychodynamics
  "Plumb the inner depths of your data types"
  (thoughts [x] "The data type's innermost thoughts")
  (feelings-about [x] [x y] "Fellings about self or other"))

                                        ; (feelings-about "hey")
(extend-type java.lang.String
  Psychodynamics
  (thoughts [x] (str x " thinks, 'Truly, the character defines the data type"))
  (feelings-about
    ([x] (str x " is longing for a simpler way of life"))
    ([x y] (str x " is envious of " y "'s simpler way of life"))))

(thoughts "blorb")

(feelings-about "schmorb")
(feelings-about "schmorb" 2)

;; record

(defrecord WereWolf [name title])

(WereWolf. "David" "London Tourist")

(->WereWolf "Jacob" "Lead Shirt Discarder")

(map->WereWolf {:name "Lucian" :title "CEO of Melodrama"})

(def jacob (->WereWolf "Jacob" "Lead Shirt Discarder"))

(.name jacob)
(:name jacob)
(get jacob :name)
(= jacob (->WereWolf "Jacob" "Lead Shirt Discarder"))
(= jacob {:name "Jacob" :title "Lead Shirt Discarder"})
(assoc jacob :title "Lead Third Wheel")
(dissoc jacob :title)

(defprotocol WereCreature
  (full-moon-behavior2 [x]))

(defrecord WereWolf2 [name title]
  WereCreature
  (full-moon-behavior2 [x]
    (str name " will howl and murder2")))

(full-moon-behavior2 (map->WereWolf2 {:name "Lucian" :title "CEO of Melodrama2"}))
