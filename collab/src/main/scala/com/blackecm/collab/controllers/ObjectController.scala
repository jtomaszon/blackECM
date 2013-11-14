package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Post

class ObjectController extends CollabStack {

  post("/") {
    Map("id" -> 1)
  }

  put("/:id") {
    Map("id" -> params("id"))
  }

  delete("/:id") {
    Map("id" -> params("id"))
  }

  get("/:id") {
    Map("id" -> params("id"))
  }

}
