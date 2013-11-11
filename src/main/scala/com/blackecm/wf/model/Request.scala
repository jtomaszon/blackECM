package com.blackecm.wf.model

// TODO: parse map from request

class Request(var id: Long, var workflow_id: Long, var user_id: Long)
  extends HasId {

  override def toString =
    s"Request: id: $id; workflow_id: $workflow_id; user_id: $user_id"
}

object Request {
  def apply(id: Long, workflow_id: Long, user_id: Long): Request =
    new Request(id, workflow_id, user_id)


  def apply(workflow_id: Long, user_id: Long): Request =
    new Request(-1L, workflow_id, user_id)
}
