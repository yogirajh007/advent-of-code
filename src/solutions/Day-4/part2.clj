#!/usr/bin/env bb
(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-4")]
         (doall (line-seq rdr))))

(def call-order (str/split (first input) #","))

(def clean-data (map #(str/split (str/trim %) #"\s+")  (filter (complement empty?) (rest input))))

(def input-boards (map vec (partition 25 (reduce concat clean-data))))

(defn gen-hashmap [{:keys [index] :as hm} key]
  (if (nil? (hm key))
    (assoc hm key (vector index)
           :index (inc index))
    (assoc hm key (conj (hm key) index)
           :index (inc index))))


(def number-map (reduce gen-hashmap
                        {:index 0}
                        (reduce concat clean-data)))

(defn mark-as-x [board num]
  (map (fn [x] (if (= x num) "X" x)) board))

(defn mark-number-on-board-as-x [board number]
  (mark-as-x board number))

(defn check-if-every-x [coll]
  (some true? (map #(every? (fn [x] (= x "X")) %) coll)))

(defn check-bingo [board]
  (let [hz-vec (partition 5 board)
        ver-vec (apply mapv vector hz-vec)]
    (or (check-if-every-x hz-vec) (check-if-every-x ver-vec))))

(defn mark-number-in-board [boards number]
  (vec (map #(mark-number-on-board-as-x % number) boards)))

(defn last-winning-board [call-order boards last-bingo-boards number]
  (if (empty? boards)
    (* (Integer/parseInt number) (reduce + (map #(Integer/parseInt %) (filter #(not= "X" %) (first last-bingo-boards)))))
    (->> (mark-number-in-board boards (first call-order))
         ((fn [x] (let [bingoed-boards (filter check-bingo x)] (if (empty? bingoed-boards)
                                                     {:boards (remove check-bingo x)
                                                      :last-bingo-boards last-bingo-boards
                                                      :number number}
                                                     {:boards (remove check-bingo x)
                                                      :last-bingo-boards bingoed-boards
                                                      :number (first call-order)}))))
         ((fn [x] (last-winning-board (rest call-order) (:boards x) (:last-bingo-boards x) (:number x)))))))

(time (last-winning-board call-order input-boards nil 0))

;; "Elapsed time: 483.108583 msecs" // HUGE
 ;; 2980
