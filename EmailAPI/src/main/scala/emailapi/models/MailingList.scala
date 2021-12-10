package emailapi.models

// use case class
// with companion object.
// where would addEmailAccount go?
// what else goes into a companion object
// what is the best Collection to use to store emails.


case class MailingList(name: String, emails: Array[EmailAccount]) {

  def addEmailAccount(email: String, name: String) {
    emails :+ EmailAccount(name, email)
  }
}
