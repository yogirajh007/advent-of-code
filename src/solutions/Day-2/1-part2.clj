#!/usr/bin/env bb

(require '[clojure.string :as str])

(defn split [x]
  ((fn [[a b]] [a (Integer/parseInt b)]) (str/split x #" ")))

(defn navigate [{:keys [x y aim]} [direction steps]]
  (case direction
    "forward" {:x (+ x steps)
               :y (+ y (* aim steps))
               :aim aim}
    "down" {:x x
            :y y
            :aim (+ aim steps)}
    "up" {:x x
          :y y
          :aim (- aim steps)}))

(with-open [rdr (clojure.java.io/reader "src/input/day-2")]
  (->> rdr
       line-seq
       (map split)
       doall
       (reduce navigate {:x 0
                         :y 0
                         :aim 0})
       (#(* (:x %) (:y %)))))
