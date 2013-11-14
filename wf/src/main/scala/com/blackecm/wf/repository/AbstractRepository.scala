package com.blackecm.wf.repository

import com.blackecm.wf.model.HasId
import java.util.concurrent.atomic.AtomicLong
import org.slf4j.LoggerFactory

class AbstractRepository[T <: HasId] {
  val logger =  LoggerFactory.getLogger(getClass)
  private var repository: List[T] = List()
  private val idCounter = new AtomicLong(0L)

  def findAll = repository

  def find(id: Long) = repository find (_.id == id)

  def create(item: T) = {
    item.id = idCounter.incrementAndGet
    repository = repository ::: List(item)
    logger info(s"created: $item")
    item
  }

  def update(item: T) = {
    val index = repository.indexOf(find(item.id))
    repository = repository.patch(index, Seq(item), 1)
    logger info(s"updated: $item")
    item
  }

  def removeById(id: Long) = {
    val item = find(id)
    repository = repository diff List(item)
    logger info(s"deleted: $item")
    null
  }
}
