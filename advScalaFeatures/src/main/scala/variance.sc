

// Invariant decoder;

trait Animal
case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal

// this assignment only compiles if
// f B <:< A
val animal1: Animal = Cat("Max")
val animal2: Animal = Dog("Dolly")
implicitly[Dog <:< Animal]

// it is invarient on A
trait InvariantDecoder[A] {
  def decode(s: String): Option[A]
}


object InvariantCatDecoder extends InvariantDecoder[Cat] {
  val CatRegex = """Cat\((\w+\))""".r
  def decode(s: String): Option[Cat] = s match {
    case CatRegex(name) => Some(Cat(name))
    case _ => None
  }
}

// this compiles
InvariantCatDecoder.decode("Cat(Max)")

// however the following does not;
//val invariantAnimalDecoder: InvariantDecoder[Animal] = InvariantCatDecoder

// Covariant Decoder; decode string to an object...
trait CovariantDecoder[+A] {
  def decode(s: String): Option[A]
}

object CovariantCatDecoder extends CovariantDecoder[Cat]{
  val CatRegex = """Cat\((\w+\))""".r
  def decode(s: String): Option[Cat] = s match {
    case CatRegex(name) => Some(Cat(name))
    case _ => None
  }
}

// is possible due to covariance
implicitly[CovariantDecoder[Cat] <:< CovariantDecoder[Animal]]

// is now possible;
val covariantAnimalDecoder: CovariantDecoder[Animal] = CovariantCatDecoder

covariantAnimalDecoder.decode("Cat(Ulysse)")


// Contravariant encoder; object to string

trait Encoder[-A]{
  def encode(a: A): String
}

object AnimalEncoder extends Encoder[Animal] {
  def encode(a: Animal): String = a.toString
}

// this one can do;
val catEncoder: Encoder[Cat] = AnimalEncoder


// collection example;
val cats: Vector[Cat] = Vector(Cat("Max"))
val animals: Vector[Animal] = cats

val catsAndDogs = cats :+ Dog("Medor")