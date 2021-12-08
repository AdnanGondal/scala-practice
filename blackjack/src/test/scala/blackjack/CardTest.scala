package blackjack
import org.scalatest.FunSuite

class CardTest extends FunSuite {

  test("blackjack.Card gets correct string") {
    val card = new Card("4",'D')
    assert(card.getString() == "4D")
  }

  test("blackjack. Card gets correct points for number rank") {
    val card = new Card("4",'D')
    assert(card.points == 4)
  }

  test("blackjack. Card gets correct points for ace rank") {
    val card = new Card("A",'D')
    assert(card.points == 11)
  }


  test("blackjack. Card gets correct points for king rank") {
    val card = new Card("K",'C')
    assert(card.points == 10)
  }


}
