(ns book-brave-clojure.ch6.ch6)

(ns-name *ns*)

'inc

(map inc [1 2])
'(map inc [1 2])

(def great-books ["East of Eden" "The Glass Bead Game"])

(ns-interns *ns*)

(deref #'book-brave-clojure.ch6.ch6/great-books)

(defn- privatef
  [])
