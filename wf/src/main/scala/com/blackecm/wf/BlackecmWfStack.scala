package com.blackecm.wf

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

trait BlackecmWfStack extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  notFound {
    Map("message" -> "Invalid request")
  }

  def invalidParameters = {
    UnprocessableEntity(Map("message" -> "Invalid parameters"))
  }

}
