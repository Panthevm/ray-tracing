(ns app.ray
  (:require [clojure.core.matrix           :as m]
            [clojure.core.matrix.operators :as o]
            [app.objects.sphere :as sphere]))

(defn lambert [sphere intersection ray]
  (max 0 (m/dot ray
                (sphere/normal sphere intersection))))

(defn first-hit [world pt ray]
  (->> (reduce (fn [world v]
                 (if-let [i (sphere/intersection v pt ray)]
                   (conj world [i v])
                   world))
               []
               world)
       (sort-by #(m/distance (first %) pt))
       first))

(defn send-ray [scene src ray]
  (if-let [[loc obj] (first-hit scene src ray)]
    (* (lambert obj loc ray) (:color obj))
    0))

(defn color-at [world camera x y]
  (let [ray (m/normalise (o/- [x y 0] camera))]
    (send-ray world camera ray)))

(defn trace [{:keys [width height]} scene camera]
  (let [buffered-image (java.awt.image.BufferedImage. width height java.awt.image.BufferedImage/TYPE_BYTE_GRAY)
        coords (for [x (range 1 width)
                     y (range 1 height)]
                 [x y])
        colors (pmap (fn [[x y]]
                       [x y (color-at scene camera x y)])
                     coords)]

    (doseq [[x y c] colors]
      (.setRGB buffered-image x y
               (.getRGB (java.awt.Color.
                         (float c) (float c) (float c)))))
    buffered-image))
