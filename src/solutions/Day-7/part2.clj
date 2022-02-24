(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-7")]
             (doall (line-seq rdr))))

(def data (mapv #(Integer/parseInt %) (str/split (first input) #",")))

(defn solution []
  (let [min-num (apply min data)
        max-num (apply max data)]
    (apply min (map #(reduce (fn [acc val] (let [cost (abs (- val %))]
                                             (+ acc (/ (* cost (inc cost)) 2))))
                             0
                             data)
                    (range min-num max-num)))))

(time (solution))

;; "Elapsed time: 1153.069459 msecs"
;; 98119739
