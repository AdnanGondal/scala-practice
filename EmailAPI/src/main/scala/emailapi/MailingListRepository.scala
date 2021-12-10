package emailapi

import emailapi.models.{EmailAccount, MailingList}

import scala.util.{Success, Try}

case class MailingListRepository(mailingLists: Map[String,MailingList]) {

  def createMailingList(name: String): MailingListRepository ={
    MailingListRepository(mailingLists + (name -> MailingList(name,Array[EmailAccount]())))
  }

  def addEmailToList(account: EmailAccount, listName: String): Option[MailingListRepository] = ???

  def getMailingList(name: String): Option[MailingList] = {
    mailingLists.get(name)
  }

  def deleteMailingList(name: String): Try[String] = ???

  def removeEmailFromMailingList(email: String, mailingListName: String): Try[String] = ???
}

object MailingListRepository {

  def init(): MailingListRepository={
    MailingListRepository(Map[String,MailingList]())
  }
}

