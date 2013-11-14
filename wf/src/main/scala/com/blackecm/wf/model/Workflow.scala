package com.blackecm.wf.model

class Workflow(var id: Long, var name: String) extends HasId {
  override def toString = s"Workflow: id: $id; name: $name"
}

object Workflow {
  def apply(id: Long, name: String): Workflow = new Workflow(id, name)
  def apply(name: String): Workflow = apply(-1L, name)
}
