package com.blackecm.wf.model

class Request(id: Long, workflow_id: Long, user_id: Long,
              fields: Map[String, _]) extends HasId(id)

object Request {
  def apply(id: Long, workflow_id: Long, user_id: Long,
            fields: Map[String, _]): Request =
    new Request(id, workflow_id, user_id, fields)


  def apply(workflow_id: Long, user_id: Long,
            fields: Map[String, _]): Request =
    new Request(-1, workflow_id, user_id, fields)
}
