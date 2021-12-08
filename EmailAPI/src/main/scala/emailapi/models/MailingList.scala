package emailapi.models


class MailingList(name: String, emails: Array[EmailAccount]) {

  def addEmailAccount(email: String, name: String) {
    emails :+ EmailAccount(name, email)
  }
}
