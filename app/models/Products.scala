package models

import java.util.Date

import anorm.SqlParser._
import anorm._
import play.api.db.DB

case class Products (id: Option[Int], name: String, price: Float, supermarket_id: Int, check_id:Int)

object Products{

  val parser: RowParser[Products] = {
    get[Option[Int]]("id") ~
    get[String]("name") ~
    get[Float]("price") ~
    get[Int]("supermarket_id") ~
    get[Int]("check_id") map {
      case id ~ name ~ price ~ supermarket_id ~ check_id => Products(id, name, price, supermarket_id, check_id)
    }
  }
  /*
  def create(user_id: Int, date: Date): Unit = {
    DB.withConnection {
      implicit connection =>
        SQL("INSERT INTO check{date, users_id} VALUES({{date},{users_id})"
        ).on('user_id -> user_id, 'date -> date).executeUpdate
    }
  }
  */
}