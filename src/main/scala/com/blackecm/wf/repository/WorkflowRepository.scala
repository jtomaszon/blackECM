package com.blackecm.wf.repository


object WorkflowRepository {
  var repository: List[Workflow] = List()

  private def nextId = repository match {
    case List() => 1L
    case _ => repository.maxBy(_.id).id + 1
  }

  def findAll = repository

  def find(id: Long) = {
    repository find (_.id == id) match {
      case Some(wf) => wf
      case None => NilWorkflow
    }
  }

  def create(w: Workflow) = {
    val wf = Workflow(nextId, w.name)
    repository = repository :+ wf
  }

  def update(w: Workflow) = {
    val index = repository.indexOf(find(w.id))
    repository = repository.patch(index, Seq(w), index)
  }
}
