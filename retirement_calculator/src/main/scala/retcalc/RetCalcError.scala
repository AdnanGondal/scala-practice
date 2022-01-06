package retcalc

import cats.data.ValidatedNel

sealed abstract class RetCalcError(val message: String)
// only one message;

object RetCalcError {
  // new type of error shown;
  type RetCalcResult[A] = ValidatedNel[RetCalcError, A]
  // is a type Alias for ValidedNel...

  // one case class per type of error message
  case class MoreExpensesThanIncome(income: Double, expenses: Double)
    extends RetCalcError(s"Expenses: $expenses >= $income. You will never be able to save enough to retire !")

  case class ReturnMonthOutOfBounds(month: Int,maximum: Int)
    extends RetCalcError(s"Cannot get the return for month $month. Accepted range: 0 to $maximum")

 // if value cannot be parsed into a number.
  case class InvalidNumber(name: String, value: String)
    extends RetCalcError(s"Invalid number for $name: $value")

  // when an argument is wrong.
  case class InvalidArgument(name: String, value: String, expectedFormat: String)
    extends RetCalcError(s"Invalid format for $name. Expected: $expectedFormat, actual:$value")


}
