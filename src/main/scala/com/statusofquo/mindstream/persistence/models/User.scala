package com.statusofquo.mindstream.persistence
package models


case class User(id: Option[UserId], username: String, password: String, firstname: String, lastname: String)
