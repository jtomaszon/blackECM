package com.blackecm.wf.repository


class WorkflowRepository {
  var repository: List[Workflow] = List()

  def all = repository
  def byId(id: Long) = {
    val index = repository find { w => w.id == id }
//    repository(index)
  }
  def create(w: Workflow) = repository = repository :+ w
  def update(w: Workflow) = {
    val index = repository find { wf => w.id == wf.id }
//    repository = repository.patch(index, w, index)
  }
}
