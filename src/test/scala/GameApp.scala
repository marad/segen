import seng.core.{Spatial, Game}
import seng.event._
import seng.gfx.lwjgl.LwjglGraphics

class GameApp extends Game(new LwjglGraphics) {
  def prepareEvents() = {
    val spatial = new Spatial
    List(
      new CreateEntityEvent(spatial),
      new MoveEvent(spatial.id, 100, 50, 0)
    )
  }
}

object GameApp {
  def main(args: Array[String]): Unit = {
    (new GameApp).start(800, 600)
  }
}

