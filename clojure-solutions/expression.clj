(load-file "proto.clj")
(load-file "parser.clj")
(def constant constantly)
(defn variable [name] #(% name))

(defn operation [op] (fn [& args] (fn [vars] (apply op (map #(% vars) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn divideEvaluate [& args] (cond (some zero? (rest args)) Double/POSITIVE_INFINITY
                                    (= (count args) 1) (/ 1.0 (first args))
                                    :else (apply / args)))
(def divide (operation divideEvaluate))
(defn meanEvaluate [& args] (/ (apply + args) (count args)))
(def mean (operation meanEvaluate))
(defn varnEvaluate [& args] (let [m (/ (apply + args) (count args))]
                              (- (/ (apply + (map #(* % %) args)) (count args))
                                 (* m m))))
(def varn (operation varnEvaluate))
(defn negate [arg] (subtract arg))

(def operation {'+      add
                '-      subtract
                '*      multiply
                '/      divide
                'negate negate
                'mean   mean
                'varn   varn})

(defn pars [constCtor varCtor opCtors]
  (letfn [(parser [s]
            (cond
              (number? s) (constCtor s)
              (symbol? s) (varCtor (str s))
              :else (apply (opCtors (first s)) (mapv parser (rest s)))
              ))]
    parser))

(def parseExpression (pars constant variable operation))

(defn parseFunction [s] (parseExpression (read-string s)))

;Object
(def toString (method :toString))
(def toStringInfix (method :toStringInfix))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def getArgs (field :args))


(def ZERO)
(def ConstantProto
  (let [value (field :val)]
    {
     :evaluate      (fn [this _] (value this))
     :toString      (fn [this] (str (value this)))
     :toStringInfix (fn [this] (str (value this)))
     :diff          (fn [_ _] ZERO)
     }))
(defn ConstantCtor [this val]
  (assoc this :val val))
(def Constant (constructor ConstantCtor ConstantProto))
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def VariableProto
  (let [name (field :name)] {
                             :evaluate      (fn [this vars] (vars (proto-get this :shortName)))
                             :toString      (fn [this] (str (name this)))
                             :toStringInfix (fn [this] (str (name this)))
                             :diff          (fn [this var] (if (= (name this) var) ONE ZERO))
                             }))
(defn VariableCtor [this name]
  (assoc this :name name :shortName (str (Character/toLowerCase ^char (first name)))))
(def Variable (constructor VariableCtor VariableProto))


(def OperationProto {
                     :evaluate      (fn [this vars] (apply (proto-get this :op) (map #(evaluate % vars) (getArgs this))))
                     :toString      (fn [this] (str "(" (proto-get this :symbol) " " (clojure.string/join " " (map toString (getArgs this))) ")"))
                     :toStringInfix (fn [this] (str "(" (toStringInfix (first (getArgs this))) " " (proto-get this :symbol) " " (toStringInfix (second (getArgs this))) ")"))
                     :diff          (fn [this var] ((proto-get this :diffFunc) (getArgs this) (map (fn [arg] (diff arg var)) (getArgs this))))
                     })


(defn OperationCtor [op symbol diffFunc]
  (fn [this & args]
    (assoc this :op op :symbol symbol :args args :diffFunc diffFunc)))


(def Add (constructor (OperationCtor + '+
                                     (fn [args dArgs] (apply Add dArgs))
                                     ) OperationProto))
(def Subtract (constructor (OperationCtor - '-
                                          (fn [args dArgs] (apply Subtract dArgs))
                                          ) OperationProto))

(def NegateProto {
                  :prototype     OperationProto
                  :toStringInfix (fn [this] (str (proto-get this :symbol) " " (toStringInfix (first (getArgs this)))))
                  })
(def Negate (constructor (OperationCtor - 'negate
                                        (fn [args dArgs] (apply Negate dArgs))) NegateProto))

(def Multiply)
(defn diffMultiply [args diffArgs]
  (apply Add (map (fn [i]
                    (Multiply (nth diffArgs i) (apply Multiply (concat (take i args) (drop (inc i) args)))))
                  (range (count args)))))
(def Multiply (constructor (OperationCtor * '* diffMultiply) OperationProto))

(def Divide (constructor (OperationCtor divideEvaluate '/
                                        (fn [args dArgs]
                                          (if (= (count args) 1)
                                            (Divide (Negate (first dArgs)) (Multiply (first args) (first args)))
                                            (let [den (apply Multiply (rest args))]
                                              (Divide (Subtract (Multiply (first dArgs) den) (Multiply (first args) (diffMultiply (rest args) (rest dArgs))))
                                                      (Multiply den den)))))
                                        ) OperationProto))

(def Mean (constructor (OperationCtor meanEvaluate 'mean (fn [args dArgs] (Divide (apply Add dArgs) (Constant (count args))))) OperationProto))
(def Varn (constructor (OperationCtor varnEvaluate 'varn
                                      (fn [args dArgs] (Multiply (Constant 2)
                                                                 (Subtract (apply Mean (map #(Multiply %1 %2) args dArgs))
                                                                           (Multiply (apply Mean args) (apply Mean dArgs))))))
                       OperationProto))


(def AbsC (constructor (OperationCtor (fn [arg1 arg2] (Math/sqrt (+ (* arg1 arg1) (* arg2 arg2)))) 'absc
                                      constantly) OperationProto))
(def PhiC (constructor (OperationCtor (fn [arg1 arg2] (Math/atan2 arg1 arg2)) 'phic
                                      constantly) OperationProto))

(def objectOperation
  {'+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'mean   Mean
   'varn   Varn
   'absc   AbsC
   'phic   PhiC})



(def parseExpressionObject (pars Constant Variable objectOperation))

(defn parseObject [s] (parseExpressionObject (read-string s)))

;Parser

(def *digit (+char "1234567890"))
(def *letter (+char (str (apply str (mapv char (range 65 91))) (apply str (mapv char (range 97 123))))))
(def *number (+map read-string (+str (+seqf #(flatten %&) (+opt (+char "-")) (+plus *digit) (+opt (+seq (+char ".") (+plus *digit))) ))))

(def *numberCtor
  (+map #(Constant %)
        *number))

(def *variable (+str (+plus (+char "xyzXYZ"))))
(def *variableCtor (+map #(Variable %) *variable))

(def *value)
(def *witespace (+star (+char " \t\n\r")))
(def *ws (+ignore *witespace))

(defn *createStr [s]
  (+str (apply +seq (map #(+char (str %)) s))))

(def *operator (+or (+char "+-*/") (*createStr "absc") (*createStr "phic")))
(def *unaryOp (*createStr "negate"))

(defn myConj [coll arg]
  (reverse (cons arg (reverse coll))))

(def *expr)
(defn *parseLeft [charParser nextParser]
  (+map (partial reduce (fn [left [op right]]
                          ((get objectOperation (symbol (str op))) left right)))
        (+seqf cons (delay nextParser) *ws
               (+star (+seq *ws charParser *ws (delay nextParser))))))

(defn *parseRight [charParser nextParser]
  (+map (partial reduce (fn [left [right op]]
                          ((get objectOperation (symbol (str op))) right left)))
        (+seqf (fn [[& args] lastArg] (reverse (myConj args lastArg)))
               (+star (+seq *ws (delay nextParser) *ws charParser)) *ws (delay nextParser))))
(def *parsePrim (+or (+map #((get objectOperation (symbol (first %))) (second %))
                           (+seq *unaryOp *ws (delay *parsePrim))) *numberCtor *variableCtor
                     (+seqn 1 (+char "(") *ws (delay *expr) *ws (+char ")"))))


(def *parseAbscPhic (*parseRight (+or (*createStr "absc") (*createStr "phic")) *parsePrim))

(def *parseMulDiv (*parseLeft (+char "*/") *parseAbscPhic))
(def *parsePlusMinus (*parseLeft (+char "+-") *parseMulDiv))

(def *expr *parsePlusMinus)

(def parseObjectInfix
  (+parser (+seqn 0 *ws (delay *expr) *ws)))
