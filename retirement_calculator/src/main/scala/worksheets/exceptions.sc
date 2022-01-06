case class Person(name: String, age: Int)
case class AgeNegativeException(message: String) extends Exception(message)

def createPerson(description: String): Person = {
  val split = description.split(" ")
  val age = split(1).toInt
  if (age < 0)
    throw AgeNegativeException(s"age: $age should be > 0")
  else
    Person(split(0), age)
}

try {
  createPerson("Adnan -2")
} catch {
  case AgeNegativeException(m) => println(m)
}

def averageAge(descriptions: Vector[String]): Double = {
  val total = descriptions.map(createPerson).map(_.age).sum
  total / descriptions.length
}

import scala.util.control.NonFatal
def personsSummary(personsInput: String): String = {
  val descriptions = personsInput.split("\n").toVector
  val avg = try {
    averageAge(descriptions)
  } catch {
    case e:AgeNegativeException =>
      println(s"one of the persons has a negative age: $e")
      0
    case NonFatal(e) =>
      println(s"something was wrong in the input: $e")
      0
  }
  s"${descriptions.length} persons with an average age of $avg"
}

personsSummary(
  """John 25
    |Sharleen 45""".stripMargin)

personsSummary(
  """John -25
    |Sharleen 45""".stripMargin)