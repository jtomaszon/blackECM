package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Post
import org.slf4j.{Logger, LoggerFactory}

case class PostIn(place:Option[String], text:Option[String])

class PostController extends CollabStack {

  val logger =  LoggerFactory.getLogger(getClass)

  post("/") {
    val post = parsedBody.extract[PostIn]
    if (post.place.isDefined && post.text.isDefined) {
      Map("id" -> 1)
    }
    else {
      invalidParameters
    }
  }

  put("/:id") {
    val post = parsedBody.extract[PostIn]
    val id = params("id")
    if (post.text.isDefined && id.forall(_.isDigit)) {
      Map("id" -> 1)
    }
    else {
      invalidParameters
    }
  }

  delete("/:id") {
    val id = params("id")
    if (id.forall(_.isDigit)) {
      Map("id" -> params("id"))
    }
    else {
      invalidParameters
    }
  }

  get("/timeline/:place") {
    Seq(
      Post(1, "daniel", "Oi Babaca", 0, 0, 1, Seq(Post(2, "daniel", "Babaca eh voce", 0, 0, 1, Seq()))),
      Post(2, "daniel", "Testando", 0, 0, 0, Seq())
    )
  }

  get("/:id") {
    val id = params("id")
    if (id.forall(_.isDigit)) {
      Post(params("id").toInt, "daniel", "Oi Babaca", 0, 0, 1, Seq(Post(2, "daniel", "Babaca eh voce", 0, 0, 1, Seq())))
    }
    else {
      invalidParameters
    }
  }
  
}
