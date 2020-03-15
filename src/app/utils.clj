(ns app.utils)

(defn square [x]
  (* x x))

(defn sqrt [x]
  (Math/sqrt x))

(defn quadratic [a b c]
  (if (zero? a)
    (/ (- c) b)
    (let [D (- (square b) (* 4 a c))]
      (when (> D 0)
        (let [discroot (sqrt D)]
          (min (/ (+ (- b) discroot) (* 2 a))
               (/ (- (- b) discroot) (* 2 a))))))))
