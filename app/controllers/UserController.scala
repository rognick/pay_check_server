package controllers

import java.util._

import models.User
import play.api.mvc._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

object UserController extends Controller{

  implicit val reads: Reads[User] = (
    (JsPath \ "id").read[Option[Int]] and
      (JsPath \ "login_name").read[String] and
      (JsPath \ "password").read[String] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "birthday").read[Date] and
      (JsPath \ "email").read[String] and
      (JsPath \ "mobile").read[String]
    )(User.apply _)

  implicit  val  writs: Writes[User] = (
    (JsPath \ "id").write[Option[Int]] and
      (JsPath \ "login_name").write[String] and
      (JsPath \ "password").write[String] and
      (JsPath \ "first_name").write[String] and
      (JsPath \ "last_name").write[String] and
      (JsPath \ "birthday").write[Date] and
      (JsPath \ "email").write[String] and
      (JsPath \ "mobile").write[String]
    )(unlift(User.unapply))

  def create = Action(parse.json){ request =>
    request.body.validate[User].map {
      case user =>
        User.create(user)
        Created(Json.toJson(user))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatJson(e))
    }
  }

  def getUsers = Action{
    Ok(Json.toJson(User.findAll()))
  }

  def update = Action(parse.json){
    request =>
      request.body.validate[User].map {
        case user =>
          User.update(user)
          Ok(Json.toJson(user))
      }.recoverTotal {
        e => BadRequest(JsError.toFlatJson(e))
      }
  }

  def getUser(id: Int) = Action{
    Ok(Json.toJson(User.getUser(id)))
  }
}