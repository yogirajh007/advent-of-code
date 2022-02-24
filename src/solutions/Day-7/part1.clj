(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-7")]
             (doall (line-seq rdr))))

(def data (mapv #(Integer/parseInt %) (str/split (first input) #",")))

(defn middle-value [vect]
  (when-not (empty? vect)
    (vect (quot (count vect) 2))))

(def mv (middle-value (vec (sort data))))

(defn solution []
  (reduce (fn [acc val] (+ acc (abs (- val mv)))) 0 data))

(time (dotimes [_ 100]
        (solution)))

;"Elapsed time: 19.22825 msecs"
;353800
