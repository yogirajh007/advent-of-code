#!/usr/bin/env bb

(require '[clojure.string :as str])

(def input (with-open [rdr (clojure.java.io/reader "src/input/day-6")]
             (doall (line-seq rdr))))

(def data (mapv #(Integer/parseInt %) (str/split (first input) #",")))



(defn add-new [{:keys [origlist newitems] :as acc} item]
  #_(println "acc:  " acc "item:  " item)
  (if (= item 0)
    {:origlist (concat origlist [6])
     :newitems (concat newitems [8])}
    {:origlist (concat origlist [(dec item)])
     :newitems newitems}))

(defn populate-lfish [list]
  (try (let [{:keys [origlist newitems]} (reduce add-new {} list)]
         (cond (empty? (remove nil? newitems))
               origlist
               :else
               (concat origlist (remove nil? newitems))))
       (catch Exception e
         (println e))))

(defn sum-and-append [list index target]
  #_(println "list " list "index  " index "tg   " target)
  (try (if (= index target)
         list
         (sum-and-append (populate-lfish list) (inc index) target))
       (catch Exception e
         (println e))))

(def reference-data (vec (range 1 6)))


(defn solution []
  (let [values (mapv #(count (sum-and-append (vector %) 0 80)) reference-data)]
    (reduce (fn [acc val] (+ acc (values (dec val)))) 0 data)))

(time (solution))

;;Sub-Optimal 
;; "Elapsed time: 3099.910708 msecs"
;; 356190
