(ns shmap)


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
    (let [first-symbol (first symbols)
          second-symbol (second symbols)]
      (if (seq symbols)
        (recur (cond
                 (and (symbol? first-symbol)
                      (symbol? second-symbol))
                 (rest (rest symbols))

                 (not (symbol? first-symbol))
                 (rest (rest symbols))

                 (not (symbol? second-symbol))
                 (rest symbols))
               (merge res
                      (cond
                        (and (symbol? first-symbol)
                             (symbol? second-symbol))
                        {(keyword first-symbol) first-symbol
                         (keyword second-symbol) second-symbol}

                        (not (symbol? first-symbol))
                        {first-symbol first-symbol}

                        (not (symbol? second-symbol))
                        {(keyword first-symbol) first-symbol}
                        )))
        res))))

(let [one 1
      two 2
      ten 10
      three 3
      res {:one 1, :ten 10, :three 3}]
  ;; f
  [(shmap :three three
          one
          two
          ten )
   ;; 2
   (shmap one
          :three three
          two
          ten )
   ;; 3
   (shmap one
          two
          :three three
          ten )
   ;; l
   (shmap one
          two
          ten
          :three three)
   ]


  )

(comment
  (map #(= % res)
       ))
