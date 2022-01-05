def divide(x: Double, y: Double): Either[String, Double] = {
  if (y==0)
      Left(s"$x cannot be divided by zero")
  else Right(x/y)
}

divide(6,3)

divide(1,0)
 // conventially: Left represents errors
// Right represents the right value

// Left type contains error string.
// Right type contains the computed value

def getPersonAge(name: String, db: Map[String, Int]): Either[String, Int] = {
      // gives Left(arg) or Right(A)
      db.get(name).toRight(s"$name is not present in db")
}

def personDescription(name: String, db: Map[String, Int]): String =
      getPersonAge(name, db) match {
            case Right(age) => s"$name is $age years old"
            case Left(error) => error
      }

val db = Map("John" -> 25, "Rob" -> 40, "Adnan" -> 22)

personDescription("John", db)

def averageAge(name1: String, name2: String, db: Map[String, Int]):
Either[String, Double] =
      getPersonAge(name1,db).flatMap(age1 =>
      getPersonAge(name2,db).map(age2 =>
            (age1 + age2).toDouble / 2
      ))

averageAge("Johnny", "Robby", db)

// map and flatmap always transform the Right side of either
// it is Right biased

def averageAgeOf3(name1: String, name2: String, name3: String, db: Map[String,Int]):
Either[String,Double] = {
      for {
            age1 <- getPersonAge(name1,db)
            age2 <- getPersonAge(name2,db)
            age3 <- getPersonAge(name3,db)
      } yield (age1 + age2 + age3).toDouble /3
}

averageAgeOf3("Johnny","Rob","Adnans",db)