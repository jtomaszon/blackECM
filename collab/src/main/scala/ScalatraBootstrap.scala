import com.blackecm.collab._
import org.scalatra._
import javax.servlet.ServletContext
import com.blackecm.collab.controllers._

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    context.mount(new PostController, "/post/*")
    context.mount(new CommentController, "/comment/*")
    context.mount(new ObjectController, "/obj/*")
    context.mount(new LikeController, "/like/*")
    context.mount(new ShareController, "/share/*")
    context.mount(new CommunityController, "/community/*")
    context.mount(new CommunityController, "/follow/*")
  }
}
