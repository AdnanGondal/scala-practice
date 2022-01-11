// essentially a semigroup, with an empty function...

import cats.implicits._
import cats.kernel.Monoid

Monoid[Int].empty

Monoid[String].empty

Monoid[Option[Double]].empty

Monoid[Vector[Int]].empty

// common use case; fold data strucutres

Vector(1,2,3).combineAll

// ie combine all elements,
// to obtain one

val monoidMultInt: Monoid[Int] = new Monoid[Int] {
  override def empty: Int = 1

  override def combine(x: Int, y: Int): Int = x * y
}
Vector(1, 2, 3, 4).combineAll(monoidMultInt)

val (count, sum) = Vector(10, 12, 14, 8).foldMap(i => (1, i))
val average = sum.toDouble / count