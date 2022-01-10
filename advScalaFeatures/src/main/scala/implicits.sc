
case class AppContext(message: String)

// implicit value
// this value is a candidate for implicit resolution.
implicit val myAppCtx: AppContext = AppContext("implicit world")

                            // impicit paramter
def greeting(prefix: String)(implicit appCtx: AppContext): String =
  prefix + appCtx.message

greeting("hello ")
// resovles the implicit..

// Note; implicit params should have a type that has
// very few instances (NOT something like String)

// EXAMPLES;
import cats.data.ValidatedNel
case class Timeout(millis: Int)

// calls external website to get price of product;
// the service waits some time before giving up;
// this is represented by Timeout;

trait PriceService {
  def getPrice(productName: String)(implicit timeout: Timeout): ValidatedNel[String,Double]
}

// in practice there may be many such servicces;
// thus implicit paramter;
// allows one to call these functions without having
// to pass the timout argument each time

// one can also pass in other Configuration paramters;

case class Product(name: String, price: Double)

trait DataService {
  def getProduct(name: String): ValidatedNel[String,Product]
  def saveProduct(product: Product): ValidatedNel[String, Unit]
}


// we have two implmentations for above service
// in production code as well as test code;

class AppContext(implicit  val defaultTimeout: Timeout, val priceService: PriceService, val dataService: DataService)

// above context; has diffn implemntations for production
// and tests

import cats.implicits._
def updatePrice(productName: String)(implicit appContext: AppContext)
: ValidatedNel[String, Double] = {
  import appContext._
  (dataService.getProduct(productName),
    priceService.getPrice(productName)).tupled.andThen {
    case (product, newPrice) =>
      dataService.saveProduct(product.copy(price = newPrice)).map(_ =>
      newPrice
    )
  }
}


// AppContext will be either the real
// or fake implmentation of PriceService
// and DataSergice

val vec = Vector("hello","world")
vec.map(s => s -> s.length)

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
Future(println(Thread.currentThread().getName))

// IMPLICIT CONVERSION

import java.time.LocalDate

implicit def stringToLocalDate(s: String): LocalDate = LocalDate.parse(s)

// ie so  a string object, can be parsed into a LocalDate;

"2018-09-01".getDayOfWeek

// now LocalDate methods, can be applied to String object.

// functions that accept LocalDate;

import java.time.temporal.ChronoUnit.DAYS
DAYS.between("2018-09-01", "2018-10-10")

class IntOps(val i: Int) extends AnyVal {
  def square: Int = i*i
}

implicit def intToIntoOps(i: Int): IntOps = new IntOps(i)

5.square