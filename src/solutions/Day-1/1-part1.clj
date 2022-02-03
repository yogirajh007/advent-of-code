#!/usr/bin/env bb

(defn count-increasing [{:keys [list num]} b]
  (if (= 0 (count list))
    {:list (conj list b)
     :num 0}
    (if (< (last list) b)
      {:list (conj list b)
       :num (inc num)}
      {:list (conj list b)
       :num num})))


(with-open [rdr (clojure.java.io/reader "../../input/day-1")]
  (:num (reduce count-increasing {:list []
                                  :num 0} (map #(Integer/parseInt %) (line-seq rdr)))))
