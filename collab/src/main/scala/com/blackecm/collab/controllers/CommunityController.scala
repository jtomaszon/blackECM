package com.blackecm.collab.controllers

import org.scalatra._
import scalate.ScalateSupport
import com.blackecm.collab.CollabStack
import com.blackecm.collab.models.Community
import com.blackecm.collab.models.Person

class CommunityController extends CollabStack {

  post("/") {
    Map("ok" -> true)
  }

  put("/:alias") {
    Map("ok" -> true)
  }

  delete("/:alias") {
    Map("ok" -> true)
  }

  get("/:alias") {
    Community("java", "Java Para Siempre", "Aqui nostros estamos a discutir piton", 13, true,
      Seq(Person("daniel", "Daniel Stori"), Person("pedro", "Pedro Moya")))
  }

  get("/") {
    Seq(
      Community("java", "Java Para Siempre", "Aqui nostros estamos a discutir piton", 13, true, Seq()),
      Community("dotnet", "Dot Net para Iniciantes", "No es toscon", 2, false, Seq()),
      Community("arroz", "Arroz Mijadra", "Discutiremos Arroz Mijandra", 8, false, Seq())
    )
  }
  
}
