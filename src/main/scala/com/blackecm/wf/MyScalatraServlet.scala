package com.blackecm.wf

import org.scalatra._

class MyScalatraServlet extends BlackecmWfStack {

  get("/") {
    "hi"
  }
  
}
