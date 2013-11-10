package com.blackecm.wf.repository

import com.blackecm.wf.model.HasId
import java.util.concurrent.atomic.AtomicLong

class AbstractRepository[T <: HasId] {
  private var repository: List[T] = List()

  private val idCounter = new AtomicLong(0L)

  def findAll = repository

  def find(id: Long) = {
    repository find (_.id == id)
  }

  def create(t: T) = {
    t.id = idCounter.incrementAndGet()
    repository ::= t
    t
  }

  def update(t: T) = {
    val index = repository.indexOf(find(t.id))
    repository = repository.patch(index, Seq(t), 1)
  }
}
