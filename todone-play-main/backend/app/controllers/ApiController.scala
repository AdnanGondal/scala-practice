package controllers

import javax.inject._
import models._
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import todone.data._

@Singleton
class ApiController @Inject() (
    val controllerComponents: ControllerComponents,
    val model: TasksModel
) extends BaseController {
  import JsonFormats._

  def tasks() = Action { implicit request: Request[AnyContent] =>
//    Ok(Json.toJson(DefaultTasks.defaultTasks))
    Ok(Json.toJson(model.tasks))
  }

  // needed parse.json inside Action
  // task Result transformed to either error response,
  // or succesful response and added a new task the model...
  def create() = Action(parse.json){ implicit request =>
    val taskResult = request.body.validate[Task]
    // we use this as we are certain this is the data type we want

    // request.body options
    // will this contain things like cookies etc too
    // is there any docs for this...

    taskResult.fold(
      // how do I write custom errors
      errors => BadRequest(JsError.toJson(errors)),
      task => {
        //
        if (task.title.isEmpty){
          BadRequest("Error: no name given")
        } else {
          val id = model.create(task)
          Ok(Json.toJson(id))
        }
      }
    )
  }

  def tags() = Action { implicit request: Request[AnyContent] =>
    //    Ok(Json.toJson(DefaultTasks.defaultTasks))
    Ok(Json.toJson(model.tags))
  }

  def projects() = Action {implicit request: Request[AnyContent] =>
    Ok(Json.toJson(model.projects))
  }

  def close(id: Int) = Action {implicit request: Request[AnyContent] => {
    model.close(Id(id=id)) match {
      case Some(value) => Ok(Json.toJson(value))
      case None => BadRequest("Error: Id not found")
    }
  }}


}
