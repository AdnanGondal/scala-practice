package emailapi

import emailapi.models.{EmailAccount, MailingList}

import scala.util.{Success, Try}

class MailingListRepository {

  private val mailingLists: Array[MailingList] = Array[MailingList]()

  def addEmailToList(email: String, name: String, mailingListName: String): Try[String] = {
    // Should return
    //  Success("")
    // or
    //  Failure("")
    // with an appropriate message
    Success("")
  }

  def getMailingList(name: String): MailingList = {

    //Placeholder returned list
    new MailingList("", Array[EmailAccount]())
  }

  def getAllMailingLists(): Array[MailingList] = mailingLists


  def deleteMailingList(name: String): Try[String] = {
    
    // Should return
    //  Success("")
    // or
    //  Failure("")
    // with an appropriate message
    Success("")
  }

  def removeEmailFromMailingList(email: String, mailingListName: String): Try[String] = {

    // Should return
    //  Success("")
    // or
    //  Failure("")
    // with an appropriate message
    Success("")
  }
}
