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
  (let [camera [150 150 50]
        scene  [{:color  0.85
                 :center [150 150 -200]
                 :radius 200}
                ]
        image  (ray/trace {:width 300 :height 300}
                          scene camera)]
    (view image)))
