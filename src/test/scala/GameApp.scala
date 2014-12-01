import org.lwjgl.input.Keyboard
import seng.core.{Entity, Spatial, Game}
import seng.event._
import seng.gfx.lwjgl.LwjglGraphics

class GameApp extends Game(new LwjglGraphics) {

  case class MoveCharacter(entityId: Entity.Id) extends SimpleEvent {
    def perform = {
      val events = new collection.mutable.MutableList[Event]()

      if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        events += new MoveEvent(entityId, 0, 1, 0)

      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        events += new MoveEvent(entityId, 0, -1, 0)

      if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        events += new MoveEvent(entityId, -1, 0, 0)

      if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        events += new MoveEvent(entityId, 1, 0, 0)

      this :: events.toList
    }
  }

  def startingEvents() = {
    val spatial = new Spatial
    List(
      new CreateEntityEvent(spatial),
      new MoveCharacter(spatial.id)
    )
  }
}

object GameApp {
  def main(args: Array[String]): Unit = {
    (new GameApp).start(800, 600)
  }
}

