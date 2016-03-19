;; Generic recursive function (possibility of blowing up the stack):
;;
;;(defn recursive-function [something]
;; (if (**ending-case?** something)
;;    something
;;    (cons something
;;          (recursive-function (**smaller-structure-from** something)))))

;; Generic recursive function with collecting parameter:
;;
;; (defn recursive-function [something so-far]
;;  (if (**ending-case?** something)
;;    so-far
;;    (recursive-function (**smaller-structure-from** something)
;;                        (cons something so-far))))

;; **recur** to let clojure know it's a tail-recursion so the compiler can
;; rewrite the function into a loop (and you won't blow up the stack)
;;
;; (defn recursive-function [something so-far]
;;  (if (**ending-case?** something)
;;    so-far
;;    (recur (**smaller-structure-from** something)
;;                        (cons something so-far))))

;; Instead of cons, you can also use your own **combiner** :
;;
;;(defn recursive-function [something]
;; (if (**ending-case?** something)
;;    (**ending-value** something)
;;    (**combiner** something
;;          (recursive-function (**smaller-structure-from** something)))))
;;
;; OR
;;
;; (defn recursive-function [something so-far]
;;  (if (**ending-case?** something)
;;    so-far
;;    (recursive-function (**smaller-structure-from** something)
;;                        (**combiner** something so-far))))

;; fp-oo 6.4 Exercises
;; Ex1
(+ 3 2)

(defn factorial [n]
       (if (or (= n 0)
               (= n 1))
  1
  (* n (factorial (dec n)))))

;; Ex2
(defn factorial-1 [n so-far]
       (if (or (= n 0)
               (= n 1))
         so-far
         (factorial-1 (dec n) (* n so-far))))

(defn factorial [n] (factorial-1 n 1))

(factorial 0)
(factorial 1)
(factorial 2)
(factorial 3)
(factorial 4)

;; Ex3 --> Make a recursive function that can add a sequence of numbers
(defn recursive-add [coll so-far]
 (if (empty? coll)
   so-far
   (recursive-add (rest coll) (+ (first coll) so-far))))

(recursive-add [1 2 3 4 5 6] 0)
(recursive-add [] 2)

;; Ex4 --> Make a recursive function that can multiply a sequence of numbers
(defn recursive-multiply [coll so-far]
 (if (empty? coll)
   so-far
   (recursive-multiply (rest coll) (* (first coll) so-far))))

(recursive-multiply [1 2 3 4] 1)

;; Ex 4 --> make the functions from Ex3 & Ex4 generic:
(defn recursive-function [combiner coll so-far]
  (if (empty? coll)
    so-far
    (recursive-function combiner (rest coll) (combiner (first coll) so-far))))

(recursive-function + [1 2 3 4] 0)
(recursive-function * [1 2 3 4] 1)

;; Ex5 --> Without changing the recursive-function, choose starting values for the two
;; wildcart parameters that will cause it to convert a sequence of keywords [:a :b :c]
;; to the map {:a 0, :b 0, :c 0}
(recursive-function (fn [key so-far] (assoc so-far key 0)) [:a :b :c] {})
(recursive-function (fn [key so-far] (assoc so-far key (count so-far))) [:a :b :c] {})

;; Ex6 --> we've build our own reduce function now (with a different order of arguments):
(reduce + 0 [1 2 3 4])
(reduce * 1 [1 2 3 4])
(reduce (fn [so-far val] (assoc so-far val 0))
        {}
        [:a :b :c])
(reduce (fn [so-far val] (assoc so-far val (count so-far)))
        {}
        [:a :b :c])

