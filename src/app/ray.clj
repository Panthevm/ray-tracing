(ns app.ray
  (:require [clojure.core.matrix           :as m]
            [clojure.core.matrix.operators :as o]
            [app.objects.sphere :as sphere]))

(defn lambert [sphere intersection ray]
  (max 0 (m/dot ray
                (sphere/normal sphere intersection))))

(defn first-hit [scene pt ray]
  (->> (reduce
        (fn [acc v]
          (if-let [i (sphere/intersection v pt ray)]
            (conj acc [i v])
            acc))
        []
        scene)
       (sort-by #(m/distance (first %) pt))
       first))

(defn send-ray [scene src ray]
  (when-let [[loc obj] (first-hit scene src ray)]
    (* (lambert obj loc ray) (:color obj))))

(defn color-at [scene camera x y]
  (let [ray (m/normalise (o/- [x y 0] camera))]
    (send-ray scene camera ray)))

(defn pixels [width height]
  (for [x (range 1 width)
        y (range 1 height)]
    [x y]))

(def memo-pixels (memoize pixels))

(defn trace [{:keys [width height]} scene camera]
  (let [buffered-image (java.awt.image.BufferedImage. width height java.awt.image.BufferedImage/TYPE_BYTE_GRAY)]
    (doseq [[x y] (memo-pixels width height)]
      (when-let [c (color-at scene camera x y)]
        (let [c (float c)]
          (.setRGB buffered-image x y
                   (.getRGB (java.awt.Color. c c c))))))
    buffered-image))
