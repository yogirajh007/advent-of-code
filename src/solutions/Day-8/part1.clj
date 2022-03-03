(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-8")]
             (doall (line-seq rdr))))

(def with-spaces (map  #(map str/trim (str/split % #"\|")) input))

(def data (map #(map (fn [x] (str/split x #" ")) %) with-spaces))

(def numbers-with-unique-segments #{2 4 3 7})

(defn get-counts [wordlist]
  (reduce (fn [x y]
            (let [wordcount (count y)]
              (if (contains? numbers-with-unique-segments wordcount)
                (inc x)
                x)))
          0
          wordlist))

(defn solution []
  (reduce + (map get-counts (map second data))))

(time (solution))

;; "Elapsed time: 2.504084 msecs"
;; 383
