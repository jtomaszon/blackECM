package com.blackecm.collab.models

case class Community(alias:String, 
                     name:String, 
                     description:String, 
                     numberParticipants:Int, 
                     amI:Boolean,
                     participants:Seq[Person])