// essentially a Combine type class;


import cats.implicits._
import cats.kernel.Semigroup

1 |+| 2

Vector(1,2) |+| Vector(3)

Option(1) |+| None |+| Option(2)

1.asRight |+| 2.asRight

// returns first arguemnt of type Left,
// or if no left, returns Right
1.asRight[String] |+| 2.asRight |+| "error".asLeft

1.validNel[String] |+| 2.validNel

1.validNel[String] |+| "error1".invalidNel |+| "error2".invalidNel

case class Player(name: String,price: Int)

object Player {
  implicit val playerCombine: Semigroup[Player] = new Semigroup[Player] {
    override def combine(x: Player, y: Player) = Player(x.name + " "+y.name,x.price+y.price)
  }
}

Player("Adnan",11) |+| Player("Gondal",22)