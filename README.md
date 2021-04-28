# Shmap

![bullethell](https://skieyko.weebly.com/uploads/1/2/4/2/124290592/858861435.jpg)

Property value shorthand macro, like in js. When you are too lazy to specify both key and value.

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
