package com.blackecm.collab.models

case class Post(id:Int, 
				place:String,
                text:String,
                numberLikes:Int,
                numberShares:Int,
                numberComments:Int,
                comments: Seq[Post])
