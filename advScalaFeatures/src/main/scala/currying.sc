

// function
def multiply(x: Int, y: Int): Int = x * y

// function value
val multiplyVal = (x: Int, y: Int) => x*y

val multiplyVal2 = multiply _

// convert function value to a curried function
val multiplyCurried = multiplyVal.curried

// type goes from Int => (Int => Int)
val res1 = multiplyCurried(2)
// Above is a partially applied function
val res2 = res1(5)

case class Item(description: String, price: Double)

def discount(percentage: Double)(item: Item): Item =
  item.copy(price = item.price * (1-percentage / 100))

//fully apply;

discount(10)(Item("Monitor",100))

// partially apply:
// need to tell it that we want functional value
val discount10 = discount(10) _


discount10(Item("Mouse",50))

val items = Vector(Item("Monitor", 500), Item("Laptop", 700))

items.map(discount(10))