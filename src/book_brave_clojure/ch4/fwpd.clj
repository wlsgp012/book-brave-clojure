(ns book-brave-clojure.ch4.fwpd)

(def filename "./src/book_brave_clojure/ch4/suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(convert :glitter-index "3")

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))
(parse (slurp filename) )

(defn mapify
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(def records (mapify (parse (slurp filename))))

(defn glitter-fiter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))
(glitter-fiter 3 records)


;; practice
;; 1
(map #(:name %) (glitter-fiter 3 records))
;; 2

;; 3
(defn validate
  [key value]
  (println (str key value)))

;; 4
; I could use "vals"
(defn map-to-vals
  [m]
  (map #(first (rest %)) m))

(defn to-csv-str
  [records]
  (reduce (fn [r row]
            (conj r (clojure.string/join "," (map-to-str row))))
          []
          records))

(println (to-csv-str records))
