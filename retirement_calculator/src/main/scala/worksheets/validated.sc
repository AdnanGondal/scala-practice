import cats.data._
import cats.data.Validated._
import cats.implicits._


val valid1: Validated[NonEmptyList[String],Int] = Valid(1)
// Valid type Int param,
// invalid type NonEmptyList[String]
//
// ie each error is of string type,
// and there must be at least one error!


val valid2 = 2.validNel[String]
// valud value ot 2;
// a cats method called validNel;
// where we pass in the type of error...

(valid1, valid2).mapN {case (i1,i2) => i1 +i2}
// tuple

// make invalid results
val invalid3: ValidatedNel[String, Int] = Invalid(NonEmptyList.of("error"))

val invalid4 = "another error".invalidNel[Int]

(valid1, valid2, invalid3, invalid4).mapN{case (i1,i2,i3,i4) => i1 + i2 + i3 +i4}