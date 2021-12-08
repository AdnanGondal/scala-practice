package emailapi

import courier.Defaults._
import courier._

import javax.mail.internet.InternetAddress
import scala.util._

class GmailMailer(email: String, password: String) {

  println("Configured Email: " + email)
  println("Configured Password: " + password)

  private val mailer: Mailer = Mailer("smtp.gmail.com", 587)
    .as(email, password)
    .auth(true)
    .startTls(true)()

  def sendEmail(email: String, subject: String, content: String): Unit = {
    mailer(Envelope.from(new InternetAddress(email))
      .to(new InternetAddress(email))
      .subject(subject)
      .content(Text(content))).onComplete {
      case Success(_) => println(s"Message to $email was delivered")
      case Failure(e) => println(e.toString)
    }
  }
}

