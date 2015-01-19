package seng

import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.graphics.{Texture, GL20, Color}
import com.badlogic.gdx.graphics.g2d.{Sprite, BitmapFont, SpriteBatch}
import seng.core.Scene
import seng.core.props.Positionable
import seng.event.Event

class GameApp extends ApplicationListener {
  private var events: List[Event] = List.empty
  private val currentScene: Scene = new Scene

  private var batch: SpriteBatch = null
  private var font: BitmapFont = null
  private var texture: Texture = null
  private var sprite: Sprite = null

  override def create(): Unit = {
    batch = new SpriteBatch()
    font = new BitmapFont()
    font.setColor(Color.WHITE)

    texture = new Texture(Gdx.files.internal("avatar.png"))
    sprite = new Sprite(texture)

    Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f)
  }

  override def dispose(): Unit = {
    batch.dispose()
    font.dispose()
    texture.dispose()
  }

  override def resize(witdh: Int, height: Int): Unit = { }

  override def pause(): Unit = {
  }

  override def render(): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    events = currentScene.update(events)
    currentScene.render()

    batch.begin()
    sprite.draw(batch)
    font.draw(batch, "HELLO WORLD", 200, 200)
    batch.end()
  }

  override def resume(): Unit = {
  }
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

