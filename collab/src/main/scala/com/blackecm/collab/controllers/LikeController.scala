package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Person

class LikeController extends CollabStack {

  post("/") {
    Map("numberLikes" -> 1)
  }

  get("/:id") {
    Seq(
      Person("daniel", "Daniel Stori"),
      Person("joao", "Joao Silva"),
      Person("pedro", "Pedro Paulo Mariano")
    )
  }
  
}
