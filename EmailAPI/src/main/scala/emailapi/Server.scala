package emailapi

import akka.http.javadsl.ServerBinding

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn

object Server extends App {

  //Write your code below here
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext
  val port = 8080
  val host = "localhost"

  val route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      } ~ post {
        entity(as[String]) { str =>
          complete(s"Received String : ${str}")
        }
      }
    }
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
