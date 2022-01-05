// An expression is referentially transparent if it can be replaced by
// the expressions value without changing the programs behaviour.

// Pure function; the return value depends only on the arguments passed into
// the function; and no other consideration is needed about the context in which
// the funciton is called

def pureSquare(x: Int): Int = x*x

val pureExpr = pureSquare(4) + pureSquare(3)


// example of impure function; it changes global state
var globalState = 1

def impure(x: Int): Int = {
  globalState = globalState + x
  globalState
}

val impureExpr = impure(3)

// in this case we Cannot replace the function call
// with its value

// nondeterministic function example:
import scala.util.Random
def impureRand(): Int = Random.nextInt()
impureRand()

// here the value changes for every call

// make it pure via:
def pureRand(seed: Int): Int = new Random(seed).nextInt()
pureRand(10)

// here the funciton call is referentially transparent
// and the function is a pure function.

case class Rectangle(width: Double, height: Double)

def area(r: Rectangle): Double = {
  if (r.width>5 || r.height > 5)
    throw new IllegalArgumentException("too big")
  else r.width * r.height
}

val area1 = area(3, 2)
val area2 = area(4, 2)
val total = try {
  area1 + area2
} catch {
  case e: IllegalArgumentException => 0
}