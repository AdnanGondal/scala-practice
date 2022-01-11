// type classes capture behaviour common across several types;

// ORDERING

//
Vector(1,3,2).sorted

import java.time.LocalDate
implicit val dateOrdering: Ordering[LocalDate] =
  Ordering.fromLessThan[LocalDate](_ isBefore _)

import Ordering.Implicits._
// defines useful pimped methods...

LocalDate.of(2018,5,18) < LocalDate.of(2017,1,1)

Vector(
LocalDate.of(2018, 5, 18),
LocalDate.of(2018, 6, 1)
).sorted(dateOrdering.reverse)

case class Person(name: String,age: Int)

object Person {
  implicit val personOrdering: Ordering[Person] =
    Ordering.by(_.age)
}

List(Person("Omer", 40), Person("Bart", 10)).sorted

Vector(Person("Omer", 49), Person("Bart", 102), Person("Adnan", 22)).sorted


Person("Adnan",22) > Person("Mahid",19)