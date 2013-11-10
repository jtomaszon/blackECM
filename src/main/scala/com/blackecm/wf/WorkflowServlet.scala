package com.blackecm.wf

import org.scalatra._
<<<<<<< HEAD
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

//fake repository
import com.blackecm.wf.repository._

class WorkflowServlet extends BlackecmWfStack with JacksonJsonSupport {

  // get the list of workflows
  get("/"){
     Ok(WorkflowRepository.findAll)
  }

  post("/"){
    "Criacao de workflows"
  }

  get("/:id"){
    NotFound("Buscar Workflow")
  }

  put("/:id"){
    val id = params("id").toLong
    Ok(WorkflowRepository.find(id))
  }

  delete(":id"){
    "Exclusão de Workflow"
  }
}
