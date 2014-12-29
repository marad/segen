import org.lwjgl.input.{Mouse, Keyboard}
import org.lwjgl.util.vector.Vector3f
import seng.core.Entity.Id
import seng.core.props.{Rotatable, Positionable, Renderable}
import seng.core.{Entity, Spatial, Game}
import seng.event._
import seng.gfx.lwjgl.LwjglGraphics
import seng.gfx.{Box, Quad, Camera}
import seng.gfx.VectorMath.wrapper

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

  case class MoveCamera() extends SimpleEvent {

    override def perform: List[Event] = {
      val moveSpeed = 0.01f
      val speed = 0.001f

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
        val pos = new Vector3f()
        camera.move(camera.getDirection * moveSpeed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        camera.move(-camera.getDirection * moveSpeed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
        camera.move(-camera.getRight * moveSpeed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
        camera.move(camera.getRight * moveSpeed)
      }

      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.lookAt(0, 0, 0)
      if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) camera.setPosition(0, 0, 0)


//      camera.addYaw(moveSpeed * Mouse.getDX)
//      camera.addPitch(moveSpeed * -Mouse.getDY)

      if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
        camera.yaw(-speed)
      }

      if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
        camera.yaw(speed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
        camera.pitch(-speed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
        camera.pitch(speed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_COMMA)) {
        camera.roll(-speed)
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
        camera.roll(speed)
      }

      List(this)
    }

  }

  def startingEvents() = {
    val box = new Spatial(new Box(5f))
    val quad = new Spatial(new Quad(10f))

    List(
      new CreateEntityEvent(box),
      new CreateEntityEvent(quad),
//      new ResetPosition(box.id),
//      new MoveCharacter(box.id),
//      new RotateCharacter(box.id),
//      new ResetPositionOnKeyPress(box.id, Keyboard.KEY_R),
      new MoveCamera
    )
  }
}

object GameApp {
  def main(args: Array[String]): Unit = {
    (new GameApp).start(800, 600)
  }
}

