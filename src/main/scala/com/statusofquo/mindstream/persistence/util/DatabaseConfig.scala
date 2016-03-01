package com.statusofquo.mindstream.persistence
package utils

trait DatabaseConfig extends Config {
  val driver = slick.driver.PostgresDriver

  import driver.api._

  def db = Database.forConfig("postgres")

  implicit val session: Session = db.createSession()
}
