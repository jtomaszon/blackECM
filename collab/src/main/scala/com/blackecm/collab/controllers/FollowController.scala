package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Person

class FollowController extends CollabStack {

  post("/") {
    Map("ok" -> true)
  }

  delete("/:alias") {
    Map("ok" -> true)
  }

  get("/:alias") {
    Seq(
      Person("joao", "Joaozinho Moura"),
      Person("carlos", "Carlos Goleiro")
    )
  }
  
}
