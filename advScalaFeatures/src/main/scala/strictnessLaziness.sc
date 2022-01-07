class StrictDemo {
  val stringVal = {
    println("Evaluating strictval")
    "Hello"
  }
}

val strictDemo = new StrictDemo

// RHS of assignment; the block is evaluated as
// soon as above line is instantiated

class LazyDemo {
  lazy val lazyVal = {
    println("Evaluating lazyVal")
    "Hello"
  }
}

val lazyDemo = new LazyDemo

// lazy block evaluates on use of variable:

lazyDemo.lazyVal + " World"

class LazyChain {
  lazy val val1 = {
    println("Evaluating val1")
    "hello"
  }
  lazy val val2 = {
    println("Evaluating val2")
    val1 + "lazy"
  }
  lazy val val3 = {
    println("Evaluating val3")
    val2 + " chain"
  }

}

val lazyChain = new LazyChain

lazyChain.val1

// all three values get evaluated,
// only when val3 is required
lazyChain.val3


object AppConfig {
  val greeting: String = {
    println("Loading Greeting")
    "Hello "
  }
}

def greet(name: String, greeting: => String): String = {
  if (name == "Mikael")
    greeting + name
  else
    s"I don't know you $name"
}

greet("Bob", AppConfig.greeting)
// as body of the function does not require it,
// the phrase AppConfig.greeting is not evaluated.

// however it is evalueate here:
greet("Mikael", AppConfig.greeting)


// non strict
def evenPlusOne(xs: Vector[Int]): Vector[Int] =
  xs.filter {x => println(s"filter $x"); x % 2 == 0}
    .map {x =>println(s"map $x");x+1 }

evenPlusOne(Vector(0,1,2))
// here; it first filters, and then maps!

// one can however use; the non strict withFilter method
def lazyEvenPlusOne(xs: Vector[Int]): Vector[Int] =
  xs.withFilter {x => println(s"filter $x"); x % 2 == 0}
    .map {x =>println(s"map $x");x+1 }


lazyEvenPlusOne(Vector(0, 1, 2))

def lazyEvenPlusTwoStream(xs: Stream[Int]): Stream[Int] =
  xs.filter { x => println(s"filter $x") ; x % 2 == 0 }
    .map { x => println(s"map $x") ; x + 1 }
    .map { x => println(s"map2 $x") ; x + 1 }
    
lazyEvenPlusTwoStream(Stream(0, 1, 2)).toVector
