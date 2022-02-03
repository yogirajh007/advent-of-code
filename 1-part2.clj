(defn count-increasing-with-window [{:keys [list num sum]} b]
  (if (< (count list) 3)
    {:list (conj list b)
     :num 0
     :sum (+ sum b)}
    (let [newsum (+ b (- sum (first list)))]
      (if (> newsum sum)
        {:list (conj (vec (rest list)) b)
         :sum newsum
         :num (inc num)}
        {:list (conj (vec (rest list)) b)
         :sum newsum
         :num num}))))


(with-open [rdr (clojure.java.io/reader "1-input")]
  (:num (reduce count-increasing-with-window {:list []
                                              :sum 0
                                              :num 0} (map #(Integer/parseInt %) (line-seq rdr)))))
