#!/usr/bin/env bb

(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-5")]
             (doall (line-seq rdr))))


(defn split [x]
  (str/split x #"->"))

(defn to-two-points [[p1 p2]]
  (let [[a b] (mapv #(Integer/parseInt %) (str/split p1 #","))
        [c d] (mapv #(Integer/parseInt %) (str/split p2 #","))]
    [[a b] [c d]]))

(def lines (map to-two-points (map #(mapv % [0 2]) (map #(str/split % #" ") input))))

(defn keep-only-hv [[p q]]
  (let [[a b] p
        [c d] q]
    (or (= a c) (= b d))))

(def hv-lines (filter keep-only-hv lines))

(defn simple-line-eq [acc [[x1 y1] [x2 y2]]]
  (cond (= x1 x2)
        (conj acc [[x1 y1] [x2 y2] [:x x1]])
        (= y1 y2)
        (conj acc [[x1 y1] [x2 y2] [:y y1]])
        (and (= x1 x2) (= y1 y2))
        (conj acc [[x1 y1] [x2 y2] [:x x1 :y y1]])))


(def lines-with-points-and-equation (reduce simple-line-eq [] hv-lines))

(defn extract-points [[[a b] [c d] C]]
  (cond (= 4 (count C))
        [(C 1) (C 3)]
        (= (C 0) :x)
        (mapv #(identity [(C 1) %]) (range (min b d) (inc (max b d))))
        (= (C 0) :y)
        (mapv #(identity [% (C 1)]) (range (min a c) (inc (max a c))))))

(time
 (count (filter (fn [[_ v]] (> v 1))
                (frequencies (reduce concat
                                     (map extract-points
                                          lines-with-points-and-equation))))))

;; "Elapsed time: 340.032542 msecs"
;; 7436
