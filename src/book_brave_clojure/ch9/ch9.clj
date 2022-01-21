(ns book-brave-clojure.ch9.ch9)

;; future
(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))

(println "I'll print immediatley")

(let [result (future (println "this prints once")
                     (+ 1 1))]
  (println "result :" result)
  (println "deref: " (deref result))
  (println "@: " @result))

(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "The result is:" @result)
  (println "It will be at least 3 seconds before I print"))

(deref (future (Thread/sleep 1000) 0) 10 5)

(realized? (future (Thread/sleep 1000)))

(let [f (future)]
  @f
  (realized? f))

;; delays
(def jackson-5-delay
  (delay (let [message "Just call may name and I'll be there"]
           (println "First deref:" message)
           message)))

(force jackson-5-delay)

(def gimli-headshots ["a.jpg" "b.jpg" "c.jpg"])

(defn email-user
  [email-address]
  (println "Sending headshot notification to" email-address))

(defn upload-document
  [headshot]
  true)

(let [notify (delay (email-user "and-my-axe@gmail.com"))]
  (doseq [headshot gimli-headshots]
    (future (upload-document headshot)
            (force notify))))

;; promises
(def my-promise (promise))
(deliver my-promise (+ 1 2))

(def yak-butter-international
  {:store "Yak Butter International"
   :price 90
   :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the buttter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))

(time
 (let [butter-promise (promise)]
   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
     (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
               (deliver butter-promise satisfactory-butter))))
   (println "And the winner is:" @butter-promise)))

(let [p (promise)]
  (deref p 100 "timed out"))

(let [ferengi-wisdom-promise (promise)]
  (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
  (Thread/sleep 100)
  (deliver ferengi-wisdom-promise "Whisper your way to success."))
q
