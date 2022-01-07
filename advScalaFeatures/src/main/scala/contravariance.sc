import com.sun.org.apache.bcel.internal.generic.NEW

import scala.concurrent.impl.Promise.DefaultPromise


// List is a generic type logic;
val list: List[Int] =  List(1,2,3)

// definition: List[+A]

class Animal
class Dog(name: String) extends Animal

// : if Dog <: Animal.
// does List[Dog] <: Animal
// ie the Variance question?

// List can transfer the subtype relationship of Dog and Animal to itself;
// if Yes; then the type List is COVARIANT
val lassie = new Dog("Lassie")
val hachi = new Dog("Hachi")
val laika = new Dog("Laika")

val anAnimal: Animal = lassie// dog
val myDogs: List[Animal] = List(lassie,hachi,laika)
// a list of animals      // a list of dogs

val myAnimals: List[Dog] = List(anAnimal)

// essentially the polymorphic capability is transformed to the list type

// IF NO: Invariant; no covariance relationship;
// ie list of certain type, has no relatinship with other;
      // generic type T; that has no relationship to any other type;
class MyInvariantList[T]

//is not possible: even though Dog inherits Animal,
// List of Dog is not a List of Animal (subtyping relationshop does not transfer to the invariant list)
//val myDogs: MyInvariantList[Animal] = new MyInvariantList[Dog]
val myAnimals: MyInvariantList[Animal] = new MyInvariantList[Animal]

// No; the opposite; Contravariance

class MyContravariantList[-T]
// will transfer the subtyping relationshop in the opposite way
// List[Animal] extends a List[Dog]


val myDogs2: MyContravariantList[Dog] = new MyContravariantList[Animal]

// WHY WE NEED CONTRAVARIANCE; EXAMPLE
// doctor to heal animals
trait Vet[-T] {
  def heal(animal: T): Boolean
}

val myDog = new Dog("Buddy")

def giveVet(): Vet[Dog] = new Vet[Animal] {
  override def heal(animal: Animal) = {
    println("Youll be fine")
    true
  }
}

// Vet of Dog, using a Vet of animal
// ie the Vet can heal any Animal...
val myVet: Vet[Dog] = giveVet()
myVet.heal(myDog)




