(ns book-brave-clojure.ch10.ch10
  (:require [clojure.string :as s]))

(def ^:dynamic *notification-address* "dobby@elf.org")

(binding [*notification-address* "test@elf.org"]
  *notification-address*)

(binding [*notification-address* "tester-1@elf.org"]
  (println *notification-address*)
  (binding [*notification-address* "tester-2@elf.org"]
    (println *notification-address*))
  (println *notification-address*))

(defn notify
  [message]
  (str "To: " *notification-address* "\n"
       "MESSAGE: " message))

(notify "I fell.")

(def nsname (s/replace (str (ns-name *ns*)) #"-" "_"))

(defn get-nsname
  [ns]
  (s/replace (str (ns-name ns)) #"-" "_"))

(defn get-file-path
  [nsname]
  (str "src/"
       (s/join "/" (drop-last (s/split nsname #"\.")))
       "/print-output"))

(binding [*out* (clojure.java.io/writer (get-file-path nsname))]
  (println "A man who carries a cat by the tail learns
something he can learn in no other way.
-- Mark Twain"))

(slurp (get-file-path nsname))

(println ["Print" "all" "the" "things!"])

(binding [*print-length* 1]
  (println ["Print" "all" "the" "things!"]))

(def ^:dynamic *troll-thought* nil)

(defn troll-riddle
  [your-answer]
  (let [number "man meat"]
    (when (thread-bound? #'*troll-thought*)
      (set! *troll-thought* number))
    (if  (= number your-answer)
      "TROLL: You can cross the bridge!"
      "TROLL: Time to eat you, succulent human!")))

(binding [*troll-thought* nil]
  (println (troll-riddle 2))
  (println "SUCCULENT HUMAN: Oooooh! The answer was" *troll-thought*))

(def power-source "hair")

(alter-var-root #'power-source (fn [_] "7-eleven parking lot"))

(with-redefs [*out* *out*]
  (doto (Thread. #(println "with redefs allows me to show up in the REPL"))
    .start
    .join))

(defn always-1
  []
  1)

(take 5 (repeatedly always-1))

(take 5 (repeatedly (partial rand-int 10)))

(def alphabet-length 26)

(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))

(defn random-string
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))

(defn random-string-list
  [list-length string-length]
  (doall (take list-length (repeatedly (partial random-string string-length)))))

(def orc-names (random-string-list 3000 7000))

(time (dorun (map s/lower-case orc-names)))

(time (dorun (pmap s/lower-case orc-names)))

(def orc-name-abbrevs (random-string-list 20000 300))

(time (dorun (map s/lower-case orc-name-abbrevs)))

(time (dorun (pmap s/lower-case orc-name-abbrevs)))

(def numbers [1 2 3 4 5 6 7 8 9 10])

(partition-all 3 numbers)

(pmap inc numbers)

(apply concat
       (pmap (fn [number-group] (doall (map inc number-group)))
             (partition-all 3 numbers)))

(time
 (dorun
  (apply concat
         (pmap (fn [name] (doall (map s/lower-case name)))
               (partition-all 1000 orc-name-abbrevs)))))

(defn ppmap
  [grain-size f & colls]
  (apply concat
         (apply pmap
                (fn [& pgroups] (doall (apply map f pgroups)))
                (map (partial partition-all grain-size) colls))))

(time (dorun (ppmap 1000 s/lower-case orc-name-abbrevs)))
