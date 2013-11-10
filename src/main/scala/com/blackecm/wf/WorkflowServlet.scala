package com.blackecm.wf

import org.scalatra._

class WorkflowServlet extends BlackecmWfStack {



  get("/"){
    "Listagem de Workflows"
  }

  post("/"){
    "Criacao de workflows"
  }

  get("/:id"){
    "Buscar Workflow"
  }

  put("/:id"){
    "Alteração de Workflow"
  }

  delete(":id"){
    "Exclusão de Workflow"
  }
}
