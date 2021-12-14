package emailapi

import akka.http.javadsl.ServerBinding

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import emailapi.models.{EmailAccount, MailingList, MailingLists}
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import scala.io.StdIn

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val emailFormat = jsonFormat2(EmailAccount)
  implicit val mailingListFormat = jsonFormat2(MailingList)
  implicit val repoFormat = jsonFormat1(MailingLists)
}

object Server extends App with JsonSupport {

  //Write your code below here
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext
  val port = 8080
  val host = "localhost"

  var store = MailingListRepository.init()

  val smallRoute =
    get {
      concat(
        pathSingleSlash {
          complete {
            "Captain on the bridge!"
          }
        },
        path("ping") {
          complete("PONG!")
        }
      )
    }



  val helloRoute =
    get {
      concat(
        pathSingleSlash {
          complete {
            "Captain on the bridge!"
          }
        },
        path("hello") {
          complete("HELLO")
        }
      )
    }

  val mailingListRoute = pathEndOrSingleSlash {
    concat(
      get{
        complete(MailingLists(store.mailingLists.values.toArray))
      },
      post{
        entity(as[MailingList]) { mailingList => {
          store = store.createMailingList(mailingList)
          complete(s"Created a new mailing list.")
        }
        }
      }
    )
  }

  val changeMailingListRoute =  path(Segment) { name =>
    concat(
      get {
        // check rejectEmptyResponse
        store.getMailingList(name) match{
          case Some(value) => complete(value)
          case None => complete(StatusCodes.Forbidden)
        }
      },
      post {
        entity(as[EmailAccount]){emailAcc => {
          store.addEmailToList(emailAcc,name) match {
            case Some(newStore) => {
              store = newStore
              complete(s"adding to the mailing list  ${name} with "+emailAcc)
            }
            case None => {complete(StatusCodes.Forbidden)}
          }
        }
        }
      },
      delete {
        // check out onSuccess
        complete(s"delete the mailing list: ${name}")
      }
    )
  }

  val listsEmailRoute: Route =
    pathPrefix( Segment / "email"){ name =>
      concat(
        pathEndOrSingleSlash{
          post{
            complete(s"${name} will be emailed")
          }
        }
       ,
        path(Segment) { email=>{
          concat(
            delete{
              complete(s"deleting email ${email} from list ${name}")
            }
          )
        }

        }
      )
    }

  val listsRoute: Route =
    pathPrefix("lists") {
      concat( mailingListRoute
        , changeMailingListRoute,listsEmailRoute
      )
    }

  val route = helloRoute ~ listsRoute
  // Write your code above here

  // Note: You have to bind your requestHandler or routes here
  val bindingFuture = Http().newServerAt("localhost", port).bind(route)
  // binding = Http().newServerAt("localhost", 8080).bindSync(requestHandler)
  // or
  // binding = Http().newServerAt("localhost", 8080).bind(route)

  bindingFuture.onComplete {
    case Success(value) =>
      println(s"Server is listening on http://$host:${port}")
    case Failure(exception) =>
      println(s"Failure : $exception")
      system.terminate()
  }

  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
