package models

import anorm.RowParser
import anorm.SqlParser._
import anorm.~

case class Supermarket(id: Option[Int], name: String, street: String, district: String, latitude: Float, longitude: Float, check_id: Int)

object Supermarket {

  val parser: RowParser[Supermarket] = {
    get[Option[Int]]("id") ~
      get[String]("name") ~
      get[String]("street") ~
      get[String]("district") ~
      get[Float]("latitude") ~
      get[Float]("longitude") ~
      get[Int]("check_id") map {
      case id ~ name ~ street ~ district ~ latitude ~ longitude ~ check_id =>
        Supermarket(id, name, street, district, latitude, longitude, check_id)
    }
  }

}
