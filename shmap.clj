(ns shmap
  (:require [clojure.test :refer [deftest is]]))


(defmacro shmap
  "Property value shorthand, like in js. When you are lazy to specify both key and value.

  ```
  (let [one 1
        two 2
        :three three
        ten 10]
    (shmap one
           two
           :three three
           ten)) => {:one 1, :two 2, :three 3, :ten 10}
  ```

"
  [& symbols]
  (loop [symbols symbols
         res {}]
    (let [[left right] (take 2 symbols)
          both-symbols? (every? symbol? [left right])]
      (if (seq symbols)
        (recur (rest (if (or both-symbols?
                             (not (symbol? left)))
                       (rest symbols)
                       symbols))
               (merge res
                      (cond
                        both-symbols?
                        {(keyword left) left
                         (keyword right) right}

                        (not (symbol? left))
                        {left right}

                        :else
                        {(keyword left) left}
                        )))
        res))))

(deftest basic
  (let [one 1
        two 2
        ten 10
        three 3
        expected {:two two :one one, :ten ten, :three three}]

    (is (= expected
           (shmap :three three
                  one
                  two
                  ten )))
    (is (= expected
           (shmap one
                  :three three
                  two
                  ten )))
    (is (= expected
           (shmap one
                  two
                  :three three
                  ten )
           ))
    (is (= expected
           (shmap one
                  two
                  ten
                  :three three)))
