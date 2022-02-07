#!/usr/bin/env bb

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-3")]
         (doall (line-seq rdr))))

(defn get-nth-column [x data]
  (map #(nth % x) data))

(defn filter-data [n bit data] ;; Complexity - O(n)
  (filter (fn [x]
            (= bit
               (nth x n)))
          data))

(defn to-decimal [x]
  (Integer/parseInt (apply str x) 2))


(defn rating [index data gas]
  (if (= 1 (count data))
    (to-decimal data)
    (let [freq (frequencies (get-nth-column index data))]
      (cond (= gas :o2) (if (>= (freq \1) (freq \0))
                          (rating (inc index) (filter-data index \1 data) gas)
                          (rating (inc index) (filter-data index \0 data) gas))
            (= gas :co2) (if (not (>= (freq \1) (freq \0)))
                           (rating (inc index) (filter-data index \1 data) gas)
                           (rating (inc index) (filter-data index \0 data) gas))))))

(defn solution [input]
  (* (rating 0 input :o2)
     (rating 0 input :co2)))

(time (solution input)) 

;; Total Complexity Calculation
;; Run 1 - 2 * Input = O(n)
;; Run 2 - 2 * (Output of run 1) = O(n/2)
;; Run n - 2 + (Output of run n-1) = O (1)

;; Complexity ~  (2 * n/2) * (2 * n/4) ... n  ~ O(n^2) 
