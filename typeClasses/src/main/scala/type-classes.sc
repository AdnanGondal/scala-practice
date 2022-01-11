
// type class; can define methods that are defined on all types belonging to the type class

// example

trait Combine[A] {
  def combine(x: A, y: A): A
}

object Combine{

  def apply[A](implicit combineA: Combine[A]): Combine[A] = combineA

  implicit val combineInt: Combine[Int] = new Combine[Int] {
    override def combine(x: Int, y: Int)  :Int = x + y
  }

  implicit val combineString: Combine[String] = new Combine[String] {
    override def combine(x: String, y: String) :String = x+y
  }

  implicit class CombineOps[A](val x: A)(implicit combineA: Combine[A]){
    def combine(y: A): A = combineA.combine(x,y)
  }

  implicit def combineOption[A](implicit combineA: Combine[A]): Combine[Option[A]] = new Combine[Option[A]] {
    override def combine(x: Option[A], y: Option[A]): Option[A] = {
      for {
        x <- x
        y <- y
      } yield  combineA.combine(x,y)
    }
  }

  implicit def combineVector[A: Combine]: Combine[Vector[A]] = new Combine[Vector[A]] {
    override def combine(x: Vector[A], y: Vector[A]): Vector[A] = x ++ y
  }

  implicit def combineTuple[A: Combine,B: Combine]: Combine[(A,B)] = new Combine[(A, B)] {
    override def combine(x: (A, B), y: (A, B)): (A,B) = {
      (Combine[A].combine(x._1,y._1), Combine[B].combine(x._2,y._2))
    }
  }



}

Combine[Int].combine(1,2)
Combine[String].combine("Hello ","World")

import Combine.CombineOps
2.combine(3)

val firstName = "Adnan"
val surname = "Gondal"
Combine[Vector[Int]].combine(Vector(2),Vector(1))

(1,"Hello").combine(2," World")


// general type class:

trait MyTypeClass[A]{
  // typeclass interface
}

object MyTypeClass{
  def apply[A](implicit myTypeClass: MyTypeClass[A]): MyTypeClass[A] = myTypeClass

  // create omplicit instances of trait...
  // ... for all desired types

  // and also an implict conversion to Ops class

}

