package models


import java.util.Date

import anorm.{RowParser, ~}
import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB


case class Check(id: Option[Int], date: Date, user_id: Int)

object Check {

  val parser: RowParser[Check] = {
    get[Option[Int]]("id") ~
      get[Date]("date") ~
      get[Int]("user_id") map {
      case id ~ date ~ user_id => Check(id, date, user_id)
    }
  }

  def create(user_id: Int, date: Date): Unit = {
    DB.withConnection {
      implicit connection =>
        SQL("INSERT INTO check{date, users_id} VALUES({{date},{users_id})"
        ).on('user_id -> user_id, 'date -> date).executeUpdate
    }
  }

  def getById(id: Int): Check = {
    DB.withConnection {
      implicit connection =>
        SQL("SELECT * FROM check WHERE id={id}").on('id -> id).as(Check.parser.single)
    }
  }

  def getByDate(date: Date): Option[Check] = {
    DB.withConnection {
      implicit connection =>
        SQL("").on('date -> date).as(Check.parser.singleOpt)
    }
  }

}

