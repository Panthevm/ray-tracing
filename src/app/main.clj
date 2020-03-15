(ns app.main
  (:require [app.ray :as ray])
  (:import (javax.swing JFrame JPanel)))

(defn view [image]
  (doto (JFrame. "Ray Tracing")
    (.add (proxy [JPanel] []
            (paintComponent [g]
              (proxy-super paintComponent g)
              (.drawImage g image 0 0 this))))
    (.setVisible true)
    (.setSize (.getWidth image) (.getHeight image))))

(comment
  (let [camera [150 150 200]
        scene [{:color 0.85
                :center [150 150 -600]
                :radius 300}
               {:color 0.85
                :center [250 250 -600]
                :radius 300}]
        image (ray/trace {:width 500 :height 500}
                         scene camera)]
    (view image)))
