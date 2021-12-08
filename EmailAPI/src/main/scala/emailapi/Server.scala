package emailapi

import akka.http.javadsl.ServerBinding

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Server extends App {

  //Write your code below here


  // Write your code above here

  // Note: You have to bind your requestHandler or routes here
  val binding: Future[ServerBinding]
  // binding = Http().newServerAt("localhost", 8080).bindSync(requestHandler)
  // or
  // binding = Http().newServerAt("localhost", 8080).bind(route)

  binding.onComplete {
    case Success(value) =>
      println(s"Server is listening on http://$host:${port}")
    case Failure(exception) =>
      println(s"Failure : $exception")
      system.terminate()
  }


}
