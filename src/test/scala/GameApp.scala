import org.lwjgl.input.Keyboard
import seng.core.Entity.Id
import seng.core.props.{Rotatable, Positionable, Renderable}
import seng.core.{Entity, Spatial, Game}
import seng.event._
import seng.gfx.lwjgl.LwjglGraphics

class GameApp extends Game(new LwjglGraphics) {

  case class MoveCharacter(entityId: Entity.Id) extends SimpleEvent {
    def perform = {
      var mx:Float = 0f
      var my:Float = 0f
      var mz:Float = 0f

      if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        my += 1f

      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        my -= 1f

      if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        mx -= 1f

      if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        mx += 1f

      if (Keyboard.isKeyDown(Keyboard.KEY_Q))
        mz += 0.01f

      if (Keyboard.isKeyDown(Keyboard.KEY_Z))
        mz -= 0.01f

      List(
        this, new MoveEvent(entityId, mx, my, mz)
      )
    }
  }

  case class RotateCharacter(entityId: Entity.Id) extends SimpleEvent {
    def perform = {
      var rx:Float = 0f
      var ry:Float = 0f
      var rz:Float = 0f

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) rz -= 0.1f
      if (Keyboard.isKeyDown(Keyboard.KEY_E)) rz += 0.1f
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) ry -= 0.1f
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) ry += 0.1f
      if (Keyboard.isKeyDown(Keyboard.KEY_X)) rx -= 0.1f
      if (Keyboard.isKeyDown(Keyboard.KEY_C)) rx += 0.1f

      List( this, new RotateEvent(entityId, rx, ry, rz))
    }
  }

  case class ResetPosition(toEntity: Entity.Id) extends EntityEvent {
    override def perform(entity: Entity): List[Event] = {
      entity match {
        case e: Entity with Positionable with Rotatable =>
          e.setPosition(0f, 0f, 0f)
          e.setRotation(0f, 0f, 0f)
      }
      List.empty
    }
  }

  case class ResetPositionOnKeyPress(entityId: Entity.Id, key: Int) extends SimpleEvent {
    override def perform: List[Event] =
      if (Keyboard.isKeyDown(key))
        List( this, new ResetPosition(entityId) )
      else List( this )
  }

  def startingEvents() = {
    val spatial = new Spatial
    List(
      new CreateEntityEvent(spatial),
      new ResetPosition(spatial.id),
      new MoveCharacter(spatial.id),
      new RotateCharacter(spatial.id),
      new ResetPositionOnKeyPress(spatial.id, Keyboard.KEY_R)
    )
  }
}

object GameApp {
  def main(args: Array[String]): Unit = {
    (new GameApp).start(800, 600)
  }
}

