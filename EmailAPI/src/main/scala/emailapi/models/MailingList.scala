package emailapi.models

// companion object.
// where would addEmailAccount go?
// what else goes into a companion object
// what is the best Collection to use to store emails.


case class MailingList(name: String, emails: Array[EmailAccount]) {

  def addEmailAccount(account: EmailAccount): MailingList = {
   MailingList(name,emails :+ account)
  }
}

case class MailingLists(lists: Array[MailingList])