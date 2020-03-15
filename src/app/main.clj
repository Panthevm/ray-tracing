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
  (time (let [camera [400 400 200]
              scene  [{:color  0.85
                       :center [400 400 -200]
                       :radius 400}]
              image  (ray/trace {:width 800 :height 800}
                                scene camera)]
          (view image))))
