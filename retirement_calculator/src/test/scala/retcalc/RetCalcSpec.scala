package retcalc
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.EitherValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should._



class RetCalcSpec extends AnyWordSpec with
  TypeCheckedTripleEquals with Matchers with EitherValues {
  implicit val doubleEquality: Equality[Double] =
    TolerantNumerics.tolerantDoubleEquality(0.0001)

  "RetCalc.futureCapital" should {
    "calculate the amount of savings I will have in n months" in {
      val actual = RetCalc.futureCapital(
        FixedReturns(0.04), nbOfMonths = 25 * 12,
        netIncome = 3000, currentExpenses = 2000,
        initialCapital = 10000).value
      val expected = 541267.1990
      actual should ===(expected)
    }
  }

  "RetCalc.futureCapital" should {
    "calculate how much savings will be left after having taken a pension for n months" in {
    val actual = RetCalc.futureCapital(
      FixedReturns(0.04), nbOfMonths = 40 * 12,
      netIncome = 0, currentExpenses = 2000, initialCapital =
        541267.1990).value

    val expected = 309867.53176
    actual should ===(expected)
  }
}


  "RetCalc.nbOfMonthsSaving" should {
    "calculate how long I need to save before I can retire" in {
      val actual = RetCalc.nbOfMonthsSaving(
        FixedReturns(0.04), nbOfMonthsInRetirement = 40 * 12,
        netIncome = 3000, currentExpenses = 2000, initialCapital = 10000).value
      val expected = 23 * 12 + 1
      actual should ===(expected)

    }

    "not crash if the resulting nbOfMonths is very high" in {
      val actual = RetCalc.nbOfMonthsSaving(
        FixedReturns(0.01), nbOfMonthsInRetirement = 40 * 12,
        netIncome = 3000, currentExpenses = 2999, initialCapital = 0).value
      val expected = 8280
      actual should ===(expected)
    }

    "not loop forever if I enter bad parameters" in {
      val actual = RetCalc.nbOfMonthsSaving(
        FixedReturns(0.04), nbOfMonthsInRetirement = 40 * 12,
        netIncome = 1000, currentExpenses = 2000, initialCapital = 10000).left.value
      actual should === (RetCalcError.MoreExpensesThanIncome(1000,2000))
    }

    val params = RetCalcParams(
      nbOfMonthsInRetirement = 40 * 12,
      netIncome = 3000,
      currentExpenses = 2000,
      initialCapital = 10000)

    "RetCalc.simulatePlan" should {
      "calculate the capital at retirement and the capital after death" in {
        val (capitalAtRetirement, capitalAfterDeath) =
          RetCalc.simulatePlan(
            returns = FixedReturns(0.04), params = params, nbOfMonthsSavings = 25*12).value

        capitalAtRetirement should === (541267.1990)
        capitalAfterDeath should === (309867.5316)
      }
      "use different returns for capitalisation and drawdown" in {
        val nbOfMonthsSavings = 25 * 12
        val returns = VariableReturns(
          Vector.tabulate(nbOfMonthsSavings +
            params.nbOfMonthsInRetirement)(i =>
            if (i < nbOfMonthsSavings)
              VariableReturn(i.toString, 0.04 / 12)
            else
              VariableReturn(i.toString, 0.03 / 12)))
        val (capitalAtRetirement, capitalAfterDeath) =
          RetCalc.simulatePlan(returns, params = params, nbOfMonthsSavings=nbOfMonthsSavings).value
        capitalAtRetirement should ===(541267.1990)
        capitalAfterDeath should ===(-57737.7227)
      } }



  }



}
