package models

import java.util._

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class User(id: Option[Int], login_name: String,
                password: String,
                first_name: String,
                last_name: String,
                birthday: Date,
                email: String,
                mobile: String)

object User {
/*
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
*/
  val parser: RowParser[User] = {
    get[Option[Int]]("id") ~
      get[String]("login_name") ~
      get[String]("password") ~
      get[String]("first_name") ~
      get[String]("last_name") ~
      get[Date]("birthday") ~
      get[String]("email") ~
      get[String]("mobile") map {
      case id ~ login_name ~ password ~ first_name ~ last_name ~ birthday ~ email ~ mobile =>
        User(id, login_name, password, first_name, last_name, birthday, email, mobile)
    }
  }

  def create(user: User) {
    DB.withConnection(
      implicit connection =>
        SQL("INSERT INTO users(login_name, password, first_name, last_name, birthday, email, mobile) " +
          "VALUES ({login_name}, {password}, {first_name}, {last_name}, {birthday}, {email}, {mobile})"
        ).on('login_name -> user.login_name,
            'password -> user.password,
            'first_name -> user.first_name,
            'last_name -> user.last_name,
            'birthday -> user.birthday,
            'email -> user.email,
            'mobile -> user.mobile).executeUpdate
    )
  }

  def update(user: User) :Unit = {
    DB.withConnection {
      implicit connection =>
        SQL("UPDATE users " +
          "SET login_name={login_name}, password={password}, first_name={first_name}, last_name={last_name}, " +
          "birthday={birthday}, email={email}, mobile={mobile} WHERE id={id}"
        ).on('id -> user.id,
            'login_name -> user.login_name,
            'password -> user.password,
            'first_name -> user.first_name,
            'last_name -> user.last_name,
            'birthday -> user.birthday,
            'email -> user.email,
            'mobile -> user.mobile).executeUpdate
    }
  }

  def delete(id: Int): Unit = {
    DB.withConnection {
      implicit  connection =>
        SQL("DELETE FROM users WHERE id={id}").on('id -> id).executeUpdate
    }
  }

  def getUser(id: Int):User = {
    DB.withConnection {
      implicit connection =>
        SQL("SELECT * FROM users WHERE id={id}").on('id -> id)as(User.parser.single)
    }
  }

  def getByLoginName(login: String): User = {
    DB.withConnection {
      implicit connection =>
        SQL("SELECT * FROM users WHERE login_name={login}").on('login -> login).as(User.parser.single)
    }
  }

  def getById(id: Int): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("SELECT * FROM users WHERE id={id}").on('id -> id).as(User.parser.singleOpt)
    }
  }

  def findAll(): Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * FROM users").as(User.parser *)
    }
  }

}

