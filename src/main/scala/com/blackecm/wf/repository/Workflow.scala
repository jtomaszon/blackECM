package com.blackecm.wf.repository

class Workflow(_id: Long, _name: String) {
  def id = _id
  def name = _name
}

class NilWorkflow extends Workflow(-1, "")

object Workflow {
  def apply(id: Long, name: String) = new Workflow(id, name)
}

object NilWorkflow {
  def apply() = new NilWorkflow
}
