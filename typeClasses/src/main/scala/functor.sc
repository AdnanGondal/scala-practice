import cats.Functor
import cats.implicits._

// parameter type is more genetic
def addOne[F[_] : Functor](fa: F[Int]): F[Int] = fa.map(_ + 1)
// this function thus works for many parameterised types that have map operations

addOne(Vector(1,2,3))

addOne(Option(1))

addOne(1.asRight)

def square(x: Double): Double= x*x

def squareVector: Vector[Double] => Vector[Double] = {
  Functor[Vector].lift(square)
}

squareVector(Vector(1, 2, 3))