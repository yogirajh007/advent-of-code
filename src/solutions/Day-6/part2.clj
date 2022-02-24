#!/usr/bin/env bb

(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-6")]
             (doall (line-seq rdr))))

(def data (mapv #(Integer/parseInt %) (str/split (first input) #",")))

(def init-data
  (let [init-vector (vec (take 9 (repeat 0)))]
    (reduce (fn [acc val] (update acc val inc)) init-vector data)))

(defn sum-and-append [list index target]
  (try (if (= index target)
         list
         (let [newchildren (list 0)
               newlist (vec (map-indexed (fn [index _] (cond (= index 8)
                                                                newchildren
                                                                (= index 6)
                                                                (+ (list 7) (list 0))
                                                                :else
                                                                (list (inc index)))) list))]
           (sum-and-append newlist (inc index) target)))
       (catch Exception e
         (println e))))

(defn solution []
  (reduce + (sum-and-append init-data 0 256)))

(time (solution))

;; Optimal
;; "Elapsed time: 2.067667 msecs"
;; 1617359101538
