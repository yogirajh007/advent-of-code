#!/usr/bin/env bb
(require '[clojure.string :as str])

;;Reading Input
(def input (with-open [rdr (clojure.java.io/reader "src/input/day-4")]
         (doall (line-seq rdr))))
;;

;;Extracting Data
(def call-order (str/split (first input) #","))

(def clean-data (map #(str/split (str/trim %) #"\s+")  (filter (complement empty?) (rest input))))

(def boards (map vec (partition 25 (reduce concat clean-data))))
;;

;;Creating Hashmap -> key: Number 
;;                  value: List of indices at which the number appears in input (quotient of this index with 25 will give board index)
(defn gen-hashmap [{:keys [index] :as hm} key]
  (if (nil? (hm key))
    (assoc hm key (vector index)
           :index (inc index))
    (assoc hm key (conj (hm key) index)
           :index (inc index))))


(def number-map (reduce gen-hashmap
                        {:index 0}
                        (reduce concat clean-data)))
;;

;;Helper functions to change board state
(defn mark-as-x [board num]
  (map (fn [x] (if (= x num) "X" x)) board))

(defn mark-number-on-board-as-x [index board b-w-n number]
  (if (contains? b-w-n index)
    (mark-as-x board number)
    board))
;;


;;Functions to check if Bingo condition is satisfied
(defn check-if-every-x [coll]
  (some true? (map #(every? (fn [x] (= x "X")) %) coll)))

(defn check [struct board]
  (if (= true (:done struct))
    struct
    (let [hz-vec (partition 5 board)
          ver-vec (apply mapv vector hz-vec)]
      (if (or (check-if-every-x hz-vec) (check-if-every-x ver-vec))
        {:done true
         :board board}
        struct))))

(defn check-bingo [boards]
  (let [op (reduce check boards)]
    (if (= true (:done op))
      {:value true
       :board (:board op)}
      {:value false
       :board nil})))
;;

;;Funtcion to mark numbers in board as per called number
(defn mark-number-in-board [{:keys [boards bingo] :as board-struct} number]
  (if (= bingo true)
    board-struct
    (let [indices (number-map number) ;;constant
          boards-with-number (map #(quot % 25) indices) ;;constant
          marked-boards (vec (map-indexed (fn [x y] (mark-number-on-board-as-x x y (set boards-with-number) number)) boards)) ;; O(n)
          check-bingo-op (check-bingo (mapv marked-boards boards-with-number));; O(n) => (N is subset of original input (see mapv))
          bingo-found? (:value check-bingo-op)
          bingo-board (:board check-bingo-op)]
      {:boards marked-boards
       :bingo bingo-found?
       :number number
       :bingo-board bingo-board})))
;;

(defn solution [boards call-order]
  (let [op (reduce mark-number-in-board {:boards boards
                                         :bingo false} call-order)
        bingo-board (:bingo-board op)
        unmarked-numbers-sum (reduce + (map #(Integer/parseInt %) (filter #(not= "X" %) bingo-board)))
        number (Integer/parseInt (:number op))]
    (* number unmarked-numbers-sum)))

(time (dotimes [_ 100]
        (solution boards call-order)))

;; "Elapsed time: 4035.001625 msecs" for 100 iterations
;; Average Time = 40.35 msecs

 