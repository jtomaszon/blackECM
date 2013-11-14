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
        val user_id = params("user_id").toLong
        Ok(RequestRepository.create(Request(wf.id, user_id)))
      }
      case None => halt(404, "Workflow not found.")
    }
  }

  get("/:id") {
    RequestRepository.find(params("id").toLong) match {
      case Some(item) => Ok(item)
      case None => halt(404)
    }
  }

  put("/:id"){
    val id = params("id").toLong
    RequestRepository.find(id) match {
      case Some(r) => {
        val user_id = params.getOrElse("user_id", r.user_id.toString).toLong
        val workflow_id = params.getOrElse("workflow_id", r.workflow_id.toString).toLong
        val rr = Request(id, workflow_id, user_id)
        Ok(RequestRepository.update(rr))
      }
      case None => halt(404)
    }
  }

  delete(":id"){
    val id = params("id").toLong
    Ok(RequestRepository removeById(id))
  }
}
