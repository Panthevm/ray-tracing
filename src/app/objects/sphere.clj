(ns app.objects.sphere
  (:require [clojure.core.matrix           :as m]
            [clojure.core.matrix.operators :as o]
            [app.utils  :as utils]))

(defn normal [sphere pt]
  (m/normalise
   (o/- (:center sphere) pt)))

(defn intersection [sphere pt ray]
  (let [c (:center sphere)
        a (m/dot ray ray)
        b (m/esum (o/* (o/- pt c)
                       ray))
        c (- (m/dot (o/- pt c)
                    (o/- pt c))
             (utils/square (:radius sphere)))
        n (utils/quadratic a (* 2 b) c)]
    (when n
      (o/+ (o/- n ray)
           pt))))
