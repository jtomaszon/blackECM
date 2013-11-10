package com.blackecm.wf

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class WorkflowServlet extends BlackecmWfStack with JacksonJsonSupport {

  // get the list of workflows
  get("/"){
     Ok( List( "Xoxotiha", "Maooe", "Babaca", "Peteca", "Batatinha" ) )
  }

  post("/"){
    "Criacao de workflows"
  }

  get("/:id"){
    NotFound("Buscar Workflow")
  }

  put("/:id"){
    "Alteração de Workflow"
  }

  delete(":id"){
    "Exclusão de Workflow"
  }
}
