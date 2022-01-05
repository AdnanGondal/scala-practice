
val l1 = List.empty[String]

l1.headOption

val l2 = List("Hello","World")
l2.headOption

// many functions of the SDK return Option;
// these are safe functions.
// there may also be unsafe functions;
// these throw exceptions

def personDescription(name: String, db: Map[String, Int]): String =
  db.get(name) match {
    case Some(age) => s"$name is $age years old"
    case None => s"$name is not present in db"
  }

val db = Map("John" -> 25, "Rob" -> 40)
personDescription("John", db)

def personDesc(name: String, db: Map[String,Int]): String = {
  db.get(name).map(age => s"$name is $age years old")
    .getOrElse(s"$name is not present in db")
}

personDesc("Johnny", db)

def averageAgeA(name1: String, name2: String, db: Map[String, Int]):
Option[Double] = {
  val optOptAvg: Option[Option[Double]] =
    db.get(name1).map(age1 =>
      db.get(name2).map(age2 =>
        (age1 + age2).toDouble / 2))
  optOptAvg.flatten
}

averageAgeA("John", "Rob", db)
// above uses map twice; gives a nested
// Option[Option[Double]]

// .flatten; removes a level of nesting

def avereageAgeB(name1: String, name2: String, db: Map[String, Int]): Option[Double] = {
  db.get(name1).flatMap(age1 =>
    db.get(name2).map(age2=>
      (age1 + age2).toDouble / 2))
}

// flatmap: equivilant to map, with flatten.

avereageAgeB("John","Rob",db)

def avereageAgeC(name1: String, name2: String, db: Map[String, Int]): Option[Double] = {
  for {
    age1 <- db.get(name1)
    age2 <- db.get(name2)
  } yield (age1 + age2).toDouble / 2
}

avereageAgeC("John","Rob",db)
