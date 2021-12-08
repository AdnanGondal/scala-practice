package blackjack
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class HandTest extends FunSuite {
  test("blackjack. it is initally players turn ") {
    val card = new Card("4",'D')
    val player = new Hand(ArrayBuffer(card),true)
    assert(player.turn == true)
  }

  test("blackjack. can toggle player turn ") {
    val card = new Card("4",'D')
    val player = new Hand(ArrayBuffer(card),true)
    player.toggleTurn()
    assert(player.turn == false)

  }

  test("blackjack. can get cards turn ") {
    val card = new Card("4",'D')
    val player = new Hand(ArrayBuffer(card),true)
    assert(player.getCardsString() == "4D")

  }

  test("blackjack. can get correct num of points ") {
    val card = new Card("4",'D')
    val player = new Hand(ArrayBuffer(card),true)
    assert(player.getPoints() == 4)
  }

  test("blackjack. can get correct num of points for two aces") {
    val card1 = new Card("A",'D')
    val card2 = new Card("A",'H')
    val player = new Hand(ArrayBuffer(card1,card2),true)
    assert(player.getPoints() == 21)
  }

  test("blackjack. get 7 cards ") {
    val card1 = new Card("1",'D')
    val card2 = new Card("2",'H')
    val card3 = new Card("1",'H')
    val card4 = new Card("2",'D')
    val card5 = new Card("1",'S')
    val card6 = new Card("2",'S')
    val card7 = new Card("1",'C')
    val player = new Hand(ArrayBuffer(card1,card2,card3,card4,card5,card6,card7),true)
    assert(player.getPoints() == 21)
  }

  test("blackjack. hit card works ") {
    val card1 = new Card("1",'D')
    val card2 = new Card("2",'H')
    val player = new Hand(ArrayBuffer(card1,card2),true)
    val card3 = new Card("3",'H')
    player.hit(card3)
    assert(player.getPoints() == 6)
  }

}
