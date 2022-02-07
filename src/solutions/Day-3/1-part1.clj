#!/usr/bin/env bb

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-3")]
         (doall (line-seq rdr))))

(defn get-nth-column [x]
  (map #(nth % x) input))

(defn gamma-rate [x]
  (if (> (x \0) (x \1))
    \0
    \1))

(defn epsilon-rate [x]
  (if (< (x \0) (x \1))
    \0
    \1))

(defn to-decimal [x]
  (Integer/parseInt (apply str x) 2))

(time (let [data (map frequencies (map get-nth-column (range 0 (count (first input)))))]
        (* (to-decimal (map gamma-rate data))
           (to-decimal (map epsilon-rate data)))))
