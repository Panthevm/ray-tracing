(ns app.vector
  (:require [app.utils :as utils]))

(defn add [& args] (apply map + args))
(defn sub [& args] (apply map - args))
(defn mul [& args] (apply map * args))
(defn dot [& args] (->> (apply map * args)
                        (reduce +)))

(defn magnitude
  [v]
  (utils/sqrt
   (reduce
    (fn [acc x]
      (+ acc (utils/square x)))
    0 v)))

(defn normalize
  [v]
  (let [magnitude (magnitude v)]
    (map #(/ % magnitude) v)))

(defn distance
  [v1 v2]
  (magnitude (sub v1 v2)))
