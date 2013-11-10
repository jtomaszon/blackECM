package com.blackecm.wf.model

// TODO: parse map from request

class Request(id: Long, workflow_id: Long, user_id: Long) extends HasId(id) {
  override def toString =
    s"Request: id: $id; workflow_id: $workflow_id; user_id: $user_id"
}

object Request {
  def apply(id: Long, workflow_id: Long, user_id: Long): Request =
    new Request(id, workflow_id, user_id)


  def apply(workflow_id: Long, user_id: Long): Request =
    new Request(-1, workflow_id, user_id)
}
