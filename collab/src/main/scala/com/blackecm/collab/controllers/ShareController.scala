package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Share

class ShareController extends CollabStack {

  post("/") {
    Map("id" -> 1)
  }

  get("/:id") {
    Seq(
      Share(1, 1, "Estou compartilhando aqui", "daniel"),
      Share(2, 1, "Estou compartilhando la", "java4ever")
    )
  }
  
}
