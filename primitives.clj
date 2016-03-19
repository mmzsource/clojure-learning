;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.

;; Test repl
(+ 3 4)

;; *** COLLECTION *** stuff
(first '(1 2 3))
(last '(1 2 3))
(rest (list 1 2 3))
(nth [1 2 3] 1)
(def second (fn [coll] (nth coll 1)))
(second [1 2 3])
(second [:other "types" 'work 2 (+ 9 3)])
(defn third [coll] (nth coll 2))
(third [1 2 3 4])
(reverse (range 1 5))
(empty? [])
(empty? ["Hi"])
(count "Aantal")
(count [1 2 3 4])
(count {:a 3 :b 2 :c 1})

;; Types
(sequential? [1 2 3])
(sequential? '(1 2 3))
(vector? (rest [1 2 3]))
(sequential? (rest [1 2 3]))

;; Equality
(= (vector inc dec) [inc dec])
(= [2 3] '(2 3))
(= [2 3] (rest [1 2 3]))

;; Quoting
['my 'number (+ 2 6)]
'(+ 2 3)
(eval '(+ 2 3))

;; If Then Else
(if (odd? 3)
  (prn "Odd")
  (prn "Even"))

;; *** VARARGS *** Variable number of arguments
(defn maxx
  ([x] x)
  ([x y] (if (> x y) x y))
  ([x y & more] (reduce maxx (maxx x y) more)))
(maxx 2)
(maxx 2 3)
(maxx 4 1 2 -3 2 4)

;; Explicitly *** APPLY *** ing functions
(apply + '(1 4 9 16))
(defn my-apply [function collection]
  (eval
   (cons function collection)))
(my-apply + '(1 4 9 16))
(apply + 1 [2 3])
(apply + 1 2 [3])
(apply + 1 2 3 [])

;; *** LOOPS *** --> don't use them (mostly)
(filter odd? [1 2 3 4 5])
(map inc [1 2 3 5 8])
(map * [0 1 2 3] [100 200 300 400])
(list (apply * [0 100])
      (apply * [1 200])
      (apply * [2 300])
      (apply * [3 400]))
(reduce + [1 2 3 4 5])
(take 5 [1 2 3 4 5 6 7 8 9])
(distinct [1 2 2 3 4 5 5 2 4 5 6])
(concat "what's " "up " "doc?")
(concat [1 2 3] [4 5 6] [7 8 9])
(repeat 3 2)
(take 5 (repeat 2)) ;; Pay attention! Lazy sequence at work! :-D
(interleave [1 2 3] ["a" "b" "c"] [:d :e :f :g])
(drop 3 [1 2 3 4 5 6])
(drop-last 3 [1 2 3 4 5 6])
(flatten [1 2 [3 4 (+ 2 3)]])
(partition 2 [1 2 3 4 5])
(every? odd? [1 3 5 7])
(every? even? [1 3 5 7])
(every? even? [2 4 6 7])
(remove odd? [1 2 3 4 5 6])

;; *** MAPS ***
;;
;; (it’s very common to use colon-prefixed keywords because, unlike symbols,
;; they’re self-evaluating (don’t need to be quoted). Also, keywords have the
;; nice property that they act like functions when placed as the first element
;; of a list)
;;
;; Callables ~ function or keyword. (In a way maps & vectors are also callables)
;; Callables ~ anything that behaves like a function when it's in the first position
;;             of an evaluated list
;;
{:a 1, "b" 2}
(hash-map :a 1, :b 2)
(apply hash-map [:a 1, :b 2])
(get {:a 1 :b 2} :a)
(:b {:a 1 :b 2}) ;; get the value belonging to the key ':b'
(get {} :x)
(defn do-something-to-map [function]
  (function {:a "a value", :b ""}))
(do-something-to-map :a)
(do-something-to-map count)
(assoc {:a 1 :b 2} :c 3)
(assoc {:a 1 :b 2} :c 3 :d 4 :e 5)
(merge {:a 1 :b 2} {:c 3} {:d 4 :e 5})
(dissoc {:a 1 :b 2 :c 3} :b :c)
(assoc {:a 1} :a 2222)
(merge {:a 1 :b 2 :c 3} {:a 111 :b 222 :d 4} {:b "two"})

;; *** LET *** --> reuse computations
(let [three (+ 1 2)]
  (* three three 5))
;; let expressions return a value, so you can nest them inside other expressions:
(+ 1 (let [one 1] (+ one one)) 3)
;; Multiple names in one let:
(let [one 1
      two 2]
  (+ one two))
;; Use earlier created let name within the same let parameter list:
(let [little-map {:a 1}
      bigger-map (assoc little-map :b 2)]
  bigger-map)

;; *** SETS ***
(set [1 2 1 3 1])
(contains? #{1 2 3} 2)
;; A set is also a callable. It's given a single value as an arg. If the set contains the value,
;; it's returned. If it's not, nil is returned:
(#{1 2 3} 1)
(#{1 2 3} 4)
;; Many of the usual set operations aren't pre-loaded in Clojure. You retrieve them like this:
(use 'clojure.set)
(union #{1 2} #{2 3})
(intersection #{1 2} #{2 3})
;; Return a set that is the first set without elements of the remaining sets:
(difference #{1 2} #{2 3})
;; filter (and other sequence operations result in a sequence, not a set):
(filter odd? #{1 2 3})
(set? (filter odd? #{1 2 3}))
(set? (select odd? #{1 2 3}))


;; Eerdere probeersels:

(take 10 (filter #(> % 1000) (iterate #(* % 2) 2)))
(take 5 (filter even? (repeatedly #(rand-int 100))))

;; Gemiddelde waarde van 100 random ints onder de 100
(#(/ (reduce + 0.0 %) (count %)) (take 100 (repeatedly #(rand-int 100))))

(sort-by val {:y 2, :z 1, :x 3})
(sort-by key {:y 2, :z 1, :x 3})
(sort-by count ['(1 2 3) '(1) '(1 2)])
(sort-by first [[3 1] [3 0] [2 1] [2 0] [1 1] [1 0]])
(sort-by last [[3 1] [3 0] [2 1] [2 0] [1 1] [1 0]])

;; sorteer een verzameling vectoren eerst op het eerste element van de vectoren en daar waar ze gelijk zijn op het tweede element:
(reduce concat (map #(sort-by second %)(partition-by first (sort-by first [[3 1] [3 0] [2 1] [2 0] [1 1] [1 0]]))))
;; OF (zelfde resultaat):
(mapcat #(sort-by second %)(partition-by first (sort-by first [[3 1] [3 0] [2 1] [2 0] [1 1] [1 0]])))

;; sort-by first --> ([1 1] [1 0] [2 1] [2 0] [3 1] [3 0])
;; partition-by first --> ([[1 1] [1 0]] [[2 1] [2 0]] [[3 1] [3 0]])
;; map #(sort-by second %) --> ([[1 0] [1 1]] [[2 0] [2 1]] [[3 0] [3 1]])
;; reduce concat --> ([1 0] [1 1] [2 0] [2 1] [3 0] [3 1])
;; kun je deze generieker maken? Dat je een (oneindige) lijst functies mee kan geven waar in volgorde op gesorteerd moet worden?

((fn [sq] (mapcat list sq sq)) [1 2 3]) --> (1 1 2 2 3 3)

;; Mooie functies: map, mapcat, reduce, filter, iterate, take, repeatedly, sort-by, partition-by, partition-all (!), group-by, frequencies, count, rand-int, even? odd?
;; Nog bekijken: for, some, keep, take-while, drop-while, Sierpinski triangle met 2 algorithmen


(reduce conj #{} (map #(if (contains? #{2 3 4 5} %) %) #{1 2 3}))

;; for voorbeeldje:
(for [x [0 1 2 3 4]] (inc x))

;; Een closure die een base input tot een vooraf gedefinieerde macht verheft:
(map ((fn [exp] #(Math/pow % exp)) 3) [1 2 3 4])

(map (fn MMz_ify [x] (str x "z_")) (.split "MM was here" " "))

(map #(str % "z_") (.split "MM was here" " "))

(fn MMz_ify [x] (map #(str % "z_") (.split x " ")) "MM was here")

(map {"MM" "MMz_", "mm" "mmz_"} ["aa" "mm" "MM" "zz"])

(clojure.string/replace "hummer, test, emmer, MM, bla" #"(mm|MM)" "MMz_")

((fn MMz-ifyer [string] (clojure.string/replace string #"(mm|MM)" "MMz_")) "hummer, test, TROMMEL, emmer, MM, lummel, kthxbye")

;; Cartman-ifier :
(clojure.string/replace "The color is red." #"[aeiou]"  #(str % %))








