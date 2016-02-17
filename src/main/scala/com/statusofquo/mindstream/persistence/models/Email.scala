package com.statusofquo.mindstream.persistence
package models


case class Email(id: EmailId, from: Long, to: Long, subject: String, body: String, state: String)
