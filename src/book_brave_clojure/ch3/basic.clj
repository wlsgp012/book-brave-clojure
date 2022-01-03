(ns book-brave-clojure.ch3.basic)

(when true
  (print "haha")
  (println "hoho")
  "abracadabra")

(if nil
  "This won't be the result because nil if falsey"
  "nil is falsey")

(def failed-protagonist-names
  ["Larry Potter","doreen the Explorer","The Incredible Bulk"])

(def x "\"He who must not be named\"")

(def name "Chewbacca")
(str "Ubbllblblblbl - " name)

;; map
{:first-name "Charlie"
 :last-name "McFishwish"}

{name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}

(hash-map :a 1 :b 2)

(get {:a 0 :b 1} :b)

(get {:a 0 :b 1} :c)

(get {:a 0 :b 1} :c :unicorn?)

(get-in {:a 0 :b {:c "ho num"}}
        [:b :c])

({:name "the human coffeepot"} :name)

(:a {:a 1 :b 2})

;; vector
(get [3 2 1] 0)

(vector "creepy" "full" "moon")

(conj [1 2 3] 4)

;; list
'(1 2 3 4)

(nth '(:a :b :c) 2)

(list 1 "two" {3 4})

(conj '(1 2 3) 4)

;; set
#{1 2 3}

(hash-set 1 1 2 2)

(set [3 3 3 3 4 4])

(contains? #{:a :b} :a)

(contains? #{:a :b} 3)

(:a #{:a :b})

(get #{:a nil} nil)

(get #{:a nil} :a)

;; call of function
((and (= 1 1) +) 1 2 3)

((first [+ 0]) 1 2 3)

(map inc [0 1 2 3])

;; function
(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic"
  [name]
  (str "Oh. MY. GOD" name))

(defn x-chop
  "상대를 가격할 때 손으로 내려치는 종류를 기술함."
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: " (clojure.string/join ", " things)))

;; destructuring
(defn my-first
  [[first-thing]]
  first-thing)

(defn treasure-position-announce
  [{lat :lat lng :lng}]
  (println (str "latitude: " lat))
  (println (str "longitude: " lng)))

(defn treasure-position-announce2
  [{:keys [lat lng]}]
  (println (str "latitude: " lat))
  (println (str "longitude: " lng)))

;; anonymous function
(map (fn [name] (str "Hi, " name)) ["Darth Vader" "Mr. Magoo"])

(#(* % 3) 8)

(#(identity %&) 1 "blarg" :yip)

;; closure
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)
