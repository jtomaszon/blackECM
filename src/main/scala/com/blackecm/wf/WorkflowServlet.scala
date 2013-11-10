package com.blackecm.wf

import org.scalatra._
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
    val w = parsedBody.extract[Workflow]
    WorkflowRepository.create(w)
    Ok("created")
  }

  get("/:id"){
    val id = params("id").toLong
    Ok(WorkflowRepository.find(id))
  }

  put("/:id"){
    "Alterando Workflow"
  }

  delete(":id"){
    val id = params("id").toLong
    WorkflowRepository.removeById(id)
  }
}
