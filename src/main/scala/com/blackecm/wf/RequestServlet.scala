package com.blackecm.wf

import org.scalatra._
import org.scalatra.json._
import com.blackecm.wf.model._
import com.blackecm.wf.repository._
import org.json4s.JValue

class RequestServlet extends BlackecmWfStack with JacksonJsonSupport {

  def transform(body: JValue): JValue = body.camelizeKeys

  get("/"){
     Ok(RequestRepository findAll)
  }

  post("/"){
    val wf_id = params("workflow_id").toLong
    WorkflowRepository.find(wf_id) match {
      case Some(wf) => {
        val req = Request(wf.id, params("user_id").toLong,
          Map())
        Ok(RequestRepository.create(req))
      }
      case None => halt(404, "Workflow not found.")
    }
  }

  get("/:id") {
    RequestRepository.find(params("id").toLong) match {
      case Some(item) => item
      case None => halt(404)
    }
  }

  put("/:id"){
    val id = params("id").toLong
    Ok(RequestRepository.find(id))
  }

  delete(":id"){
    "adioes"
  }
}
