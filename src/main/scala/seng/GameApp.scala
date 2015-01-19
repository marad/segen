package seng

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import seng.core.Scene
import seng.event.Event

class GameApp extends ApplicationListener {

  private var events: List[Event] = List.empty
  private var currentScene: Scene = new Scene

  def startingEvents() = {
    List( )
  }

  override def create(): Unit = { }
  override def resize(i: Int, i1: Int): Unit = { }
  override def dispose(): Unit = { }

  override def pause(): Unit = {}

  override def render(): Unit = {
    events = currentScene.update(events)
    currentScene.render()
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

