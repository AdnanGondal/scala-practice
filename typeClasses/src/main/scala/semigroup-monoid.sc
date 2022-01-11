// FP; semigroup monoid

// GOAL Of these abstractions;
// they can be very useful

// type classes can help make APIs more general, more concise and more expressive

// Semigroup - set or type with a combination function

// combination function- takes two elements of the type; and produces a third value of that type;;

trait Semigroup[T] {
  def combine(a: T, b: T): T
}
// one can make instances of semigroup that are applicable for a type we want to support

// we follow the type class pattern to make these semigroup instances useful
// it is a way of organising code to be useful...

// turn them into given instances (candidate values for context paramters)

object Semigroup{
  def apply[T](implicit instance: Semigroup[T]): Semigroup[T] = instance
  // can use semigroup to obtain new values
}


// more useful when making other APIs that
// programmers interact with

// Aim; not just support Ints and Strings but
// many other types
// that users would need.

// without semigroup;
// one has to repeat the implementation for all the
// other types

// we make things general + extensible via;
        // generic; means it recevies a type argument
def reduceThings[T](list: List[T])(implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)
// and thats it; will work for Int and Str...
// instead of us having to code this definition for all
// differrent types in our app
// one can then apply for any type; but we just
// need a semigroup instance for that type

// thus we can generalise all our combinations
// under a single mechanism
// used for very general APIs...


// APIs can be even more expressive;
// as semigroup is a type class ...
// it is possible to easily make extension methods
// for any two general types T in scope;

// AIM; we use type classes,
// so that APIs can be generalised for any
// type for which there is an instance
// for the type class in scope

// MONOIDS;

// are semigroup, but with a zero or empty element identity
// where combining any element with the zero
// element returns that element

trait Monoid[T] extends Semigroup[T] {
  def empty: T // empty + x == x is the property
}


  // as monoid is also a semigroup ,
  // it inherits the properties


  // aggregate all type classes for a type into one object;

  object IntInstances {
    implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
      override def combine(a: Int, b: Int): Int = a+b

      override def empty: Int = 0
    }

  }


// are in cats.instances.int
  object StringInstances {

    implicit val stringMonoid: Monoid[String] = new Monoid[String] {
      override def combine(a: String, b: String): String = a+b

      override def empty: String = ""
    }

  }

object Monoid{
  def apply[T](implicit instance: Monoid[T]): Monoid[T] = instance
  // can use semigroup to obtain new values
}




// one can then get rid off semigroup instances,
// as they are defined in semigroup anyway