package com.blackecm.wf.model

class Workflow(id: Long, var name: String) extends HasId(id)

object Workflow {
  def apply(id: Long, name: String): Workflow = new Workflow(id, name)
  def apply(name: String): Workflow = apply(-1L, name)
}
