package com.blackecm.collab.models

case class SocialObject(id:Int, 
                description:String,
                url:String,
                numberLikes:Int,
                numberShares:Int,
                numberComments:Int,
                comments: Seq[Post])
