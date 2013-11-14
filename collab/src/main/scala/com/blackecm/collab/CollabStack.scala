package com.blackecm.collab

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.{ TemplateEngine, Binding }
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.HttpServletRequest
import collection.mutable
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

trait CollabStack extends ScalatraServlet with ScalateSupport with JacksonJsonSupport {

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
