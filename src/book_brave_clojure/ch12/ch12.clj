(ns book-brave-clojure.ch12.ch12
  (:require [book-brave-clojure.ch10.ch10 :refer [get-file-path get-nsname]])
  (:import [java.util Date Stack]
           [java.net Proxy URI]))

(.toUpperCase "By Bluebeard's bananas!")

(.indexOf "Let's synergize our bleeding edges" "y")

(java.lang.Math/abs -3)

(java.lang.Math/PI)

(macroexpand-1 '(.toUpperCase "By Bluebeard's bananas!"))

(macroexpand-1 '(.indexOf "Let's synergize our bleeding edges" "y"))

(macroexpand-1 '(java.lang.Math/abs -3))

(new String)

(String.)

(String. "To Davey Jone's Locker with ye hardies")

(let [stack (java.util.Stack.)]
  (.push stack "Latest episode of Game of Thrones, ho!")
  stack)

(doto (java.util.Stack.)
  (.push "Latest episode of Game of Thrones, ho!")
  (.push "Whoops, I meant 'Land, Ho!"))

(macroexpand-1 '(doto (java.util.Stack.)
                  (.push "Latest episode of Game of Thrones, ho!")
                  (.push "Whoops, I meant 'Land, Ho!")))

(import java.util.Stack)
(Stack.)

(import [java.util Date Stack]
        [java.net Proxy URI])

(System/getenv)

(let [file (java.io.File. "/")]
  (println (.exists file))
  (println (.canWrite file))
  (println (.getPath file)))

(def file-path (get-file-path (get-nsname *ns*)))

(spit file-path
      "- kill dat lion brov
- chop up what nasty multi-headed snake thing")

(slurp file-path)

(let [s (java.io.StringWriter.)]
  (spit s "- capture cerynian hind like for real")
  (.toString s))

(let [s (java.io.StringReader. "- get erymanthian pig what with the tusks")]
  (slurp s))

(with-open [todo-list-rdr (clojure.java.io/reader file-path)]
  (println (first (line-seq todo-list-rdr))))
