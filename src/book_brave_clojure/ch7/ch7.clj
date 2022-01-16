(ns book-brave-clojure.ch7.ch7)

(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))

(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))
(eval (list 'def 'lucky-number (concat addition-list [10])))

(def plus1-2 (read-string "(+ 1 2)"))
(list? plus1-2)
(eval plus1-2)
(conj plus1-2 :zagglewag)

(def anonyfn (read-string "#(+ 1 %)"))
((eval  anonyfn) 3)

(read-string "'(a b c)")
(read-string "@var")
(def ignore (read-string "; ignore!\n(+ 1 2)"))

(read-string "+")
(type (read-string "+"))
(list (read-string "+") 1 2)
(read-string "map")
(type (read-string "map" ))
(quote (1 2 3))

(def onePlusOne (read-string "(1 + 1)"))
;(eval oneplusone)
(eval
 (let [infix onePlusOne]
   (list (second infix) (first infix) (last infix))))

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))

(macroexpand '(ignore-last-operand (+ 1 2 10)))

(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))
(infix (1 + 2))

(defn read-resource
  [path]
  (read-string (slurp (clojure.java.io/resource path))))

(defn read-resource-for-readability
  [path]
  (-> path
      clojure.java.io/resource
      slurp
      read-string))
