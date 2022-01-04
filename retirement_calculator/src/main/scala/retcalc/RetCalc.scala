package retcalc

import scala.annotation.tailrec

object RetCalc {


  def futureCapital(interestRate: Double, nbOfMonths: Int,
                    netIncome: Int, currentExpenses: Int, initialCapital: Double): Double = {
    (0 until nbOfMonths).foldLeft(initialCapital)(
      (acc,_) => acc *(1 + interestRate) + (netIncome-currentExpenses)

    )

  }

  def simulatePlan(interestRate: Double,
                   nbOfMonthsSavings: Int, nbOfMonthsInRetirement: Int,
                   netIncome: Int, currentExpenses: Int, initialCapital:
                   Double) : (Double, Double) = {

    val capitalAtRetirement = futureCapital(interestRate = interestRate,nbOfMonths = nbOfMonthsSavings,netIncome=netIncome,currentExpenses=currentExpenses, initialCapital = initialCapital)

    val capitalAfterDeath = futureCapital(interestRate = interestRate,nbOfMonths = nbOfMonthsInRetirement,netIncome=0,currentExpenses=currentExpenses, initialCapital = capitalAtRetirement)

    (capitalAtRetirement,capitalAfterDeath)
  }

  def nbOfMonthsSaving(interestRate: Double, nbOfMonthsInRetirement: Int, netIncome: Int, currentExpenses: Int, initialCapital: Double): Int= {

    @tailrec
    def loop(months: Int): Int = {
      val (capitalAtRetirement, capitalAfterDeath) = simulatePlan(
        interestRate = interestRate,
        nbOfMonthsSavings = months, nbOfMonthsInRetirement =
          nbOfMonthsInRetirement,
        netIncome = netIncome, currentExpenses = currentExpenses,
        initialCapital = initialCapital)

        if (capitalAfterDeath > 0.0)
          months
        else
          loop(months + 1)
    }
    if (netIncome > currentExpenses)
      loop(0)
    else
      Int.MaxValue
  }

}
