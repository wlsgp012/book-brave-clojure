(ns book-brave-clojure.ch8.ch8)

(defmacro infx-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(defmacro my-print-whoopsie
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))

(macroexpand '(my-print-whoopsie (+ 1 2)))

(defmacro unless
  [test & branches]
  (conj (reverse branches) test 'if))

(macroexpand '(unless (done-been slapped? me)
                      (slap me :silly)
                      (say "I reckon that'll learn me")))

;; syntax quoting
'+
'clojure/core/+
`+
'(+ 1 2)
`(+ 1 2)
`(+ 1 ~(inc 1))
`(+ 1 (inc 1))

(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Greate squid of Madrid, this is bad code:" (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:" (quote ~good))))
(code-critic (1 + 1) (+ 1 1))

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic2
  [bad good]
  `(do ~@(map #(apply criticize-code %)
             [["Greate squid of Madrid, this is bad code:" bad]
              ["Sweet gorilla of Manila, this is good code:" good]])))

;; unquote splicing
`(+ ~(list 1 2 3))
`(+ ~@(list 1 2 3))
(code-critic2 (1 + 1) (+ 1 1))

;; variable capture
(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
  (concat (list 'let ['message "Oh, big deal!"])
          stuff-to-do))

(with-mischief (println "Here's how I feel about that thing you did: " message))
(macroexpand '(with-mischief (println "Here's how I feel about that thing you did: " message)))

;; gensym
(gensym)
(gensym 'message)
;; auto-gensym
`(blarg# blarg#)
`(let [name# "Larry Potter"] name#)

(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))


(without-mischief (println "Here's how I feel about that thing you did: " message))

;; double evaluation
(defmacro report
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
(report (do (Thread/sleep 1000) (+ 1 1)))

(defmacro report2
  [to-try]
  `(let [result# ~to-try]
     (if result#
      (println (quote ~to-try) "was successful:" result#)
      (println (quote ~to-try) "was not successful:" result#))))
(report2 (do (Thread/sleep 1000) (+ 1 1)))

(doseq [code ['(= 1 1) '(= 1 2)]]
  (report2 code))

(defmacro doseq-macro
  [macroname & args]
  `(do
     ~@(map (fn [arg] (list macroname arg)) args)))
(doseq-macro report2 (= 1 1) (= 1 2))
