package retcalc

import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import retcalc.RetCalcError.InvalidNumber

class SimulatePlanAppSpec extends AnyWordSpec with Matchers with TypeCheckedTripleEquals{
  implicit val doubleEquality: Equality[Double] =
    TolerantNumerics.tolerantDoubleEquality(0.0001)

  "SimplePlanApp.parseInt" should {
    "return a Valid value with the correct result" in {
      SimulatePlanApp.parseInt("netIncome", "2000") should ===(Valid(2000))
    }

    "return Invalid value with the error message" in {
      println(SimulatePlanApp.parseInt("netIncome","two") should ===(Invalid(NonEmptyList.of(InvalidNumber("netIncome","two")))))
    }
  }

}
