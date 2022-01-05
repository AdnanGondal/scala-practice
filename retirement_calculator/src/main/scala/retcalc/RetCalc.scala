package retcalc

import retcalc.RetCalcError.MoreExpensesThanIncome

import scala.annotation.tailrec

case class RetCalcParams(nbOfMonthsInRetirement: Int,
                            netIncome: Int,
                            currentExpenses: Int,
                            initialCapital: Double)

object RetCalc {

  def futureCapital(returns: Returns, nbOfMonths: Int,
                    netIncome: Int, currentExpenses: Int, initialCapital: Double): Either[RetCalcError,Double] = {
    (0 until nbOfMonths).foldLeft[Either[RetCalcError,Double]](Right(initialCapital)) {
      case (accumulated, month) => for {
        acc <- accumulated
        monthlyRate <- Returns.monthlyRate(returns,month)
      } yield acc * (1+monthlyRate) + (netIncome-currentExpenses)
    }
  }

  def simulatePlan(returns: Returns,
                   nbOfMonthsSavings: Int, params: RetCalcParams) : Either[RetCalcError,(Double, Double)] = {


    for {
      capitalAtRetirement <- futureCapital(returns =OffsetReturns(returns,0),nbOfMonths = nbOfMonthsSavings,netIncome=params.netIncome,currentExpenses=params.currentExpenses, initialCapital = params.initialCapital)
      capitalAfterDeath <- futureCapital(returns = OffsetReturns(returns,nbOfMonthsSavings),nbOfMonths = params.nbOfMonthsInRetirement,netIncome=0,currentExpenses=params.currentExpenses, initialCapital = capitalAtRetirement)
    } yield (capitalAtRetirement,capitalAfterDeath)
  }

  def nbOfMonthsSaving(returns: Returns, nbOfMonthsInRetirement: Int, netIncome: Int, currentExpenses: Int, initialCapital: Double): Either[RetCalcError,Int]= {

    @tailrec
    def loop(months: Int): Either[RetCalcError,Int] = {
      simulatePlan(
        returns,
        nbOfMonthsSavings = months, params=RetCalcParams(nbOfMonthsInRetirement =
          nbOfMonthsInRetirement,
        netIncome = netIncome, currentExpenses = currentExpenses,
        initialCapital = initialCapital)) match {
        case Right((capitalAtRetirement, capitalAfterDeath)) => {
          if (capitalAfterDeath > 0.0)
            Right(months)
          else
            loop(months + 1)
        }
        case Left(err) => Left(err)
      }

    }
    if (netIncome > currentExpenses)
      loop(0)
    else
      Left(MoreExpensesThanIncome(netIncome,currentExpenses))
  }

}
