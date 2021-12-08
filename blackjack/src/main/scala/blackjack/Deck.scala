package blackjack
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Deck {
  val suites = List('S','D', 'C','H')
  val ranks = List("A","2","3","4","5","6","7","8","9","10","J","Q","K")

  var cards = ArrayBuffer[Card]()
  for (suit <- suites){
    for (rank <- ranks){
      cards  += new Card(rank,suit)
    }
  }

  def draw(): Card = cards.remove(0)

  def shuffle(): Unit = {
    cards = Random.shuffle(cards)
  }
}
