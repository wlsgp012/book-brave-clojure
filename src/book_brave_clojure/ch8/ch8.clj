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
