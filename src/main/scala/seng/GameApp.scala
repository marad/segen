package seng

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite}
import com.badlogic.gdx.graphics.{Color, Texture}
import seng.event.{SimpleEvent, CreateEntityEvent, Event}
import seng.gfx.SpriteEntity

import scala.collection.mutable

class GameApp extends Game {
  private var font: BitmapFont = null
  private var texture: Texture = null
  private var sprite: Sprite = null

  private val spriteList = new mutable.MutableList[Sprite]()

  class SpriteMoveEvent(val sprite: Sprite) extends SimpleEvent {
    var dx = 1f
    var dy = 1f

    var speed = 0.3f

    override def perform: List[Event] = {
      if (sprite.getX < 0) dx = 1
      if (sprite.getX + sprite.getWidth > 640) dx = -1
      if (sprite.getY < 0) dy = 1
      if (sprite.getY + sprite.getHeight > 480) dy = -1

      sprite.translateX(dx * speed)
      sprite.translateY(dy * speed)
      
      List(this)
    }
    
  }
  override def createEvents(): List[Event] = {
    font = new BitmapFont()
    font.setColor(Color.WHITE)

    texture = new Texture(Gdx.files.internal("avatar.png"))
    sprite = new Sprite(texture)

    val events = new mutable.MutableList[Event]()

    events += new CreateEntityEvent(new SpriteEntity(sprite))

    val plBall = new Texture(Gdx.files.internal("pl.png"))
    val random = new scala.util.Random()
    (1 to 100).foreach { index =>
      val sprite = new Sprite(plBall)
      val mover = new SpriteMoveEvent(sprite)
      sprite.setX(random.nextInt(640))
      sprite.setY(random.nextInt(480))
      mover.dx = if (random.nextBoolean()) 1 else -1
      mover.dy = if (random.nextBoolean()) 1 else -1

      events += new CreateEntityEvent(new SpriteEntity(sprite))
      events += mover
    }

    events
  }

  override def dispose(): Unit = {
    font.dispose()
    texture.dispose()
  }

  override def resize(witdh: Int, height: Int): Unit = {}
  override def pause(): Unit = {}
  override def resume(): Unit = {}
}

object GameApp {
  def main(args: Array[String]): Unit = {
    val config = new LwjglApplicationConfiguration
    config.title = "Game App"
    config.width = 640
    config.height = 480
    new LwjglApplication(new GameApp(), config)
  }
}

