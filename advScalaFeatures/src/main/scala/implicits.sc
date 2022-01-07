
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