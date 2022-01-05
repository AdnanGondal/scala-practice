package retcalc

import scala.annotation.tailrec

case class RetCalcParams(nbOfMonthsInRetirement: Int,
                            netIncome: Int,
                            currentExpenses: Int,
                            initialCapital: Double)

object RetCalc {

  def futureCapital(returns: Returns, nbOfMonths: Int,
                    netIncome: Int, currentExpenses: Int, initialCapital: Double): Double = {
    (0 until nbOfMonths).foldLeft(initialCapital) {
      case (acc, month) => acc * (1 + Returns.monthlyRate(returns, month)) + (netIncome - currentExpenses)
    }
  }

  def simulatePlan(returns: Returns,
                   nbOfMonthsSavings: Int, params: RetCalcParams) : (Double, Double) = {

    val capitalAtRetirement = futureCapital(returns : Returns,nbOfMonths = nbOfMonthsSavings,netIncome=params.netIncome,currentExpenses=params.currentExpenses, initialCapital = params.initialCapital)

    val capitalAfterDeath = futureCapital(returns = OffsetReturns(returns,nbOfMonthsSavings),nbOfMonths = params.nbOfMonthsInRetirement,netIncome=0,currentExpenses=params.currentExpenses, initialCapital = capitalAtRetirement)

    (capitalAtRetirement,capitalAfterDeath)
  }

  def nbOfMonthsSaving(returns: Returns, nbOfMonthsInRetirement: Int, netIncome: Int, currentExpenses: Int, initialCapital: Double): Option[Int]= {

    @tailrec
    def loop(months: Int): Int = {
      val (capitalAtRetirement, capitalAfterDeath) = simulatePlan(
        returns,
        nbOfMonthsSavings = months, params=RetCalcParams(nbOfMonthsInRetirement =
          nbOfMonthsInRetirement,
        netIncome = netIncome, currentExpenses = currentExpenses,
        initialCapital = initialCapital))

        if (capitalAfterDeath > 0.0)
          months
        else
          loop(months + 1)
    }
    if (netIncome > currentExpenses)
      Some(loop(0))
    else
      None
  }

}
