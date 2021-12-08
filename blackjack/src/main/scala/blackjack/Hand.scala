package blackjack

import scala.collection.mutable.ArrayBuffer


class Hand(var cards: ArrayBuffer[Card], var turn: Boolean) {
  def toggleTurn(): Unit= turn= !turn

  def getCardsString(): String = cards.map(_.getString()).mkString(", ")

  def getPoints(): Int = cards match{
    case cards if cards.length == 2 && cards(0).rank == "A" && cards(1).rank=="A" => 21
    case cards if cards.length>6 && cards.map(_.points).sum < 21 => 21
    case _ => cards.map(_.points).sum
  }

  def hit(card: Card): Unit ={
    cards.append(card)
  }

}


