package blackjack
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine

class Game() {
  var deck = new Deck()
  deck.shuffle()

  var playerHand = new Hand(ArrayBuffer(deck.draw(),deck.draw()),true)
  var dealerHand = new Hand(ArrayBuffer(deck.draw(),deck.draw()),false)

  def play(): Unit= {
    println("Welcome to Blackjack.")
    readLine("Enter any button to start.")
    while (playerHand.turn){
      playerTurn()
    }
    while (dealerHand.turn){
      dealerTurn()
    }

    println(getWinner())
  }

  def getWinner(): String = {
    val playerPoints = playerHand.getPoints()
    val dealerPoints = dealerHand.getPoints()

    if (playerPoints>21 || (dealerPoints<21 && playerPoints<dealerPoints)) "You loose"
    else if (dealerPoints>21 || (dealerPoints<21 && playerPoints>dealerPoints)) "You win"
    else if (playerPoints == dealerPoints) "Draw"
    else "Something has gone horribly wrong"
  }

  def playerTurn(): Unit ={
    val points = playerHand.getPoints()

    println(s"You cards are ${playerHand.getCardsString()}")
    println(s"Your points are: ${points}")

    if (points > 21){
      // player loose
      playerHand.toggleTurn()
    } else {
      val playerChoice = readLine("What do you want to do? (\"hit\" or \"stick\")")
      playerChoice match {
        case "hit" => {
          println("Hitting")
          playerHand.hit(deck.draw())
        }
        case "stick" => {
          playerHand.toggleTurn()
          dealerHand.toggleTurn()
        }
      }
      }
  }
  def dealerTurn(): Unit = {
    val points = dealerHand.getPoints()

    println(s"Dealer's cards are ${dealerHand.getCardsString()}")
    println(s"Dealer's points are: ${points}")

    if (points > 21){
      // dealer loose
      dealerHand.toggleTurn()
    } else if (points < 17) {
      println("Dealer hitting")
      dealerHand.hit(deck.draw())
    } else {
      dealerHand.toggleTurn()
    }

  }

}
