package seng.core

import seng.event.Event
import seng.gfx.Graphics

abstract class Game(val graphics: Graphics) {
  private var currentScene: Scene = new Scene
  private var events: List[Event] = List.empty

  def getCurrentScene = currentScene
  def setCurrentScene(scene:Scene) = {
    currentScene = scene
  }

  def prepareEvents(): List[Event]

  def start(width:Int, height:Int, fullscreen: Boolean = false) = {
    graphics.init(width, height, fullscreen)

    events = prepareEvents()

    while(!graphics.shouldCloseWindow()) {
      events = currentScene.update(events)

      graphics.clear()
      currentScene.render()
      graphics.swapBuffers()
      graphics.handleEvents()
    }
  }
}
