package seng

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import seng.core.Scene
import seng.event.Event
import seng.gfx.SpriteBatchRenderInfo

abstract class Game extends ApplicationListener {
  private var events: List[Event] = List.empty
  private val currentScene: Scene = new Scene
  private var renderInfo: SpriteBatchRenderInfo = null

  def createEvents(): List[Event]

  override def create(): Unit = {
    renderInfo = new SpriteBatchRenderInfo(new SpriteBatch())
    events = createEvents()
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f)
  }
  override def render(): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    events = currentScene update events
    renderInfo.spriteBatch.begin()
    currentScene render renderInfo
    renderInfo.spriteBatch.end()
  }
}
