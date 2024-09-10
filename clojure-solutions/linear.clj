(defn checkVectors [& vectors]
  (every? (fn [vector]
            (and (vector? vector)
                 (every? number? vector)
                 (== (count vector) (count (first vectors))))) vectors))

(defn checkMatrix [& matrices]
  (every? (fn [matrix]
            (and (vector? matrix)
                 (== (count matrix) (count (first matrices)))
                 (apply checkVectors matrix))) matrices))

(defn vectorOperation [op]
  (fn [& vectors]
    {:pre  [(apply checkVectors vectors)]
     :post [(vector? %) (every? #(= (count %) (count %1)) vectors) (every? number? %)]}
    (apply mapv op vectors)))

(def v+ (vectorOperation +))
(def v- (vectorOperation -))
(def v* (vectorOperation *))
(def vd (vectorOperation /))

(defn v*s [vector & scalars]
  {:pre  [(every? number? scalars) (vector? vector)]
   :post [(vector? %) (== (count %) (count vector)) (every? number? %)]}
  (let [scalar (apply * scalars)]
    (mapv #(* % scalar) vector)))

(defn scalar [& vectors]
  {:pre  [(apply checkVectors vectors)]
   :post [(number? %)]}
  (reduce + (apply v* vectors)))

(defn vect [& vectors]
  {:pre  [(and (apply checkVectors vectors)
               (== (count (first vectors)) 3))]
   :post [(vector? %) (== (count %) 3) (every? number? %)]}
  (reduce (fn [vector1 vector2]
            (let [[x1, y1, z1] vector1
                  [x2, y2, z2] vector2]
              [(- (* y1 z2) (* z1 y2))
               (- (* z1 x2) (* x1 z2))
               (- (* x1 y2) (* y1 x2))])
            ) vectors))

(defn transpose [matrix]
  {:pre  [(checkMatrix matrix)]
   :post [(= (count (first matrix)) (count %)) (checkMatrix %)]}
  (apply mapv vector matrix))

(defn m*s [matrix & scalars]
  {:pre  [(and (every? number? scalars) (every? vector? matrix))]
   :post [(checkMatrix % matrix)]}
  (mapv #(reduce v*s % scalars) matrix))

(defn m*v [matrix vector]
  {:pre  [(and (apply checkVectors matrix)
               (vector? vector)
               (== (count vector) (count (first matrix))))]
   :post [(vector? %) (== (count %) (count matrix)) (every? number? %)]}
  (if (empty? vector)
    (into [] (repeat (count matrix) 0))
    (apply v+ (mapv v*s (transpose matrix) vector))))

(defn m*m [& matrices]
  {:pre  [(every? (fn [matrix] (every? checkVectors matrix)) matrices)
          (every? (fn [[m1 m2]] (= (count (first m1)) (count m2))) (partition 2 1 matrices))]
   :post [(= (count %) (count (first matrices)))
          (= (count (first %)) (count (first (last matrices))))]}
  (reduce (fn [matrix1 matrix2]
            (mapv #(m*v (transpose matrix2) %) matrix1)) matrices))

(defn matrixOperation [op]
  (fn [& matrices]
    {:pre  [(apply checkMatrix matrices)]
     :post [(apply checkMatrix % matrices)]}
    (apply mapv op matrices)))

(def m+ (matrixOperation v+))
(def m- (matrixOperation v-))
(def m* (matrixOperation v*))
(def md (matrixOperation vd))

(defn sizeEquals [matrix1 matrix2]
  (if (every? number? matrix1)
    (== (count matrix1) (count matrix2))
    (and (== (count matrix1) (count matrix2))
         (every? (fn [[vector1 vector2]]
                   (sizeEquals vector1 vector2)) (map vector matrix1 matrix2)))))

(defn checkSimplex [n & vectors]
  (let [indexVector (map-indexed vector vectors)]
    (every? (fn [[index vector]]
              (if (and (every? number? vector))
                (== (- (count vector) 1) (- n index))
                (apply checkSimplex (- n index) vector))) indexVector)))

(defn simplexOperation [op1]
  (let [inner-fn (fn inner-fn [& simplexes]
                   {:pre  [(and (every? (fn [simplex] (sizeEquals (first simplexes) simplex)) simplexes)
                                (checkSimplex (- (count (first simplexes)) 1) (first simplexes)))]
                    :post [(sizeEquals % (first simplexes))]}
                   (if (every? (fn [vector] (every? number? vector)) simplexes)
                     (apply op1 simplexes)
                     (apply mapv inner-fn simplexes)))]
    inner-fn))

(def x+ (simplexOperation v+))
(def x- (simplexOperation v-))
(def x* (simplexOperation v*))
(def xd (simplexOperation vd))

