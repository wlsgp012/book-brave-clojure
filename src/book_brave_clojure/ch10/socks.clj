(ns book-brave-clojure.ch10.socks)

(def sock-varieties
  #{"darned" "argyle" "wool" "horsehair" "mulleted"
    "passive-aggressive" "striped" "polka-dotted"
    "athletic" "business" "power" "invisible" "gollumed"})

(defn sock-count
  [sock-variety count]
  {:variety sock-variety
   :count count})

(defn generate-sock-gnome
  "Create an initial sock gnome state with no socks"
  [name]
  {:name name
   :socks #{}})

(def sock-gnome (ref (generate-sock-gnome "Barumpharumph")))

(def dryer (ref {:name "LG 1337"
                 :socks (set (map #(sock-count % 2) sock-varieties))}))

(defn steal-sock
  [gnome dryer]
  (dosync
   (when-let [pair (some #(if (= (:count %) 2) %) (:socks @dryer))]
     (let [updated-count (sock-count (:variety pair) 1)]
       (alter gnome update-in [:socks] conj updated-count)
       (alter dryer update-in [:socks] disj pair)
       (alter dryer update-in [:socks] conj updated-count)))))

(steal-sock sock-gnome dryer)

(:socks @sock-gnome)

(defn similar-socks
  [target-sock sock-set]
  (filter #(= (:variety %) (:variety target-sock)) sock-set))

(similar-socks (first (:socks @sock-gnome)) (:socks @dryer))

(def counter (ref 0))

(future
  (dosync
   (alter counter inc)
   (println @counter)
   (Thread/sleep 500)
   (alter counter inc)
   (println @counter)))
(Thread/sleep 250)
(println @counter)

(defn sleep-print-update
  [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))

(def counter1 (ref 0))

(future (dosync (commute counter1 (sleep-print-update 100 "Thread A" inc))))
(future (dosync (commute counter1 (sleep-print-update 150 "Thread B" inc))))

(def receiver-a (ref #{}))

(def receiver-b (ref #{}))

(def giver (ref #{1}))

(do (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 10)
                      (commute receiver-a conj gift)
                      (commute giver disj gift))))
    (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 50)
                      (commute receiver-b conj gift)
                      (commute giver disj gift)))))
