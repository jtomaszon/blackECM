package com.blackecm.wf.repository

import com.blackecm.wf.model.HasId
import java.util.concurrent.atomic.AtomicLong
import org.slf4j.LoggerFactory

class AbstractRepository[T <: HasId] {
  val logger =  LoggerFactory.getLogger(getClass)
  private var repository: List[T] = List()

  private val idCounter = new AtomicLong(0L)

  def findAll = repository

  def find(id: Long) = {
    repository find (_.id == id)
  }

  def create(t: T) = {
    t.id = idCounter.incrementAndGet()
    repository = repository ::: List(t)
    logger info(s"created: $t")
    t
  }

  def update(t: T) = {
    val index = repository.indexOf(find(t.id))
    repository = repository.patch(index, Seq(t), 1)
    logger info(s"updated: $t")
    t
  }

  def removeById(id: Long) = {
    val t = find(id)
    repository = repository diff List(t)
    logger info(s"deleted: $t")
  }
}
