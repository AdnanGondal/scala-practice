package blackjack
import org.scalatest.FunSuite


class DeckTest extends FunSuite  {
  test("blackjack. Deck has 52 cards initially") {
    val deck = new Deck()
    assert(deck.cards.length == 52)
  }

  test("blackjack. can draw first card") {
    val deck = new Deck()
    assert(deck.draw().getString() == "AS")
  }

  test("blackjack. can draw first card and get new cards") {
    val deck = new Deck()
    deck.draw()
    assert(deck.cards.length == 51)
  }

  test("blackjack. cards shuffle") {
    val deck = new Deck()
    deck.shuffle()
    println(deck.draw().getString())
  }

}
