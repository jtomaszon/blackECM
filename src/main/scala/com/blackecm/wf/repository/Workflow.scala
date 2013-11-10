package com.blackecm.wf.repository

class Workflow(var id: Long, var name: String)

class NilWorkflow extends Workflow(-1, "")

object Workflow {
  def apply(id: Long, name: String): Workflow = new Workflow(id, name)
  def apply(name: String): Workflow = apply(-1, name)
}

object NilWorkflow {
  def apply() = new NilWorkflow
}
