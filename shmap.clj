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
         res {}
         k nil]
    (let [first-symbol (first symbols)]
      (if (seq symbols)
        (recur (if (and (symbol? first-symbol)
                        (not k))
                 (rest (rest symbols))
                 (rest symbols))
               (merge res
                      (if k
                        {k first-symbol}
                        {(keyword first-symbol) first-symbol}
                        ))
               (when-not (symbol? first-symbol)
                 first-symbol))
        res))


    )


  )

(let [one 1
      two 2
      ten 10
      three 3
      res {:one 1, :ten 10, :three 3}]
  (map #(= % res)
       [(shmap :three three
               one
               two
               ten )
        (shmap one
               :three three
               two
               ten )
        (shmap one
               two
               :three three
               ten )
        (shmap one
               two
               ten
               :three three)



        ])
  )
