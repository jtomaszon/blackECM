package com.blackecm.wf.repository


object WorkflowRepository {
  var repository: List[Workflow] = List()

  def findAll = repository

  def find(id: Long) = {
    repository find (_.id == id) match {
      case Some(wf) => wf
      case None => NilWorkflow
    }
  }

  def create(w: Workflow) = repository = repository :+ w

  def update(w: Workflow) = {
    val index = repository.indexOf(find(w.id))
    repository = repository.patch(index, Seq(w), index)
  }
}
