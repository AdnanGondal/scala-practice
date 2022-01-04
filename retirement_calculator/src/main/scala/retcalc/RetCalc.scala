package retcalc

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

}
