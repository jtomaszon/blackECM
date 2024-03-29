package com.blackecm.wf

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import com.blackecm.wf.model.Workflow

//fake repository
import com.blackecm.wf.repository._

class WorkflowServlet extends BlackecmWfStack with JacksonJsonSupport {

  // get the list of workflows
  get("/"){
    Ok(WorkflowRepository.findAll)
  }

  post("/"){
    // val w = parsedBody.extract[Workflow]
    val w = Workflow(params("name"))
    Ok(WorkflowRepository.create(w))
  }

  get("/:id"){
    val id = params("id").toLong
    Ok(WorkflowRepository.find(id))
  }

  put("/:id"){
    val id = params("id").toLong
    val w = Workflow(id, params("name"))
    Ok(WorkflowRepository.update(w))
  }

  delete("/:id"){
    val id = params("id").toLong
    Ok(WorkflowRepository.removeById(id))
  }
}
