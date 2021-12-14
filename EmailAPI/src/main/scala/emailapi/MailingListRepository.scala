package emailapi

import emailapi.models.{EmailAccount, MailingList}

import scala.util.{Success, Try}

case class MailingListRepository(mailingLists: Map[String,MailingList]) {

  def createMailingList(mailingList: MailingList): MailingListRepository ={
    MailingListRepository(mailingLists + (mailingList.name -> mailingList))
  }

  def addEmailToList(account: EmailAccount, listName: String): Option[MailingListRepository] = {
    mailingLists.get(listName) match {
      case Some(list) => Option(MailingListRepository(mailingLists + (list.name -> (list.addEmailAccount(account)))))
      case None => None
    }
  }

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

