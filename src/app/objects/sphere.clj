(ns app.objects.sphere
  (:require [app.vector :as vector]
            [app.utils  :as utils]))

(defn normal [sphere pt]
  (vector/normalize
   (vector/sub (:center sphere) pt)))

(defn intersection [sphere pt ray]
  (let [c (:center sphere)
        a (vector/dot ray ray)
        b (reduce + (vector/mul (vector/sub pt c) ray))
        c (- (vector/dot (vector/sub pt c)
                         (vector/sub pt c))
             (utils/square (:radius sphere)))
        n (utils/quadratic a (* 2 b) c)]
    (when n
      (vector/add (vector/mul [n n n] ray) pt))))
