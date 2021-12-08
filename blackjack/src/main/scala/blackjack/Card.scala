package blackjack

class Card(val rank: String, val suit: Char ) {

  def getString(): String = f"${rank}${suit}"

  val points: Int = rank match{
    case "K" | "Q" | "J"  => 10
    case "A" => 11
    case _ => rank.toInt
  }
}
