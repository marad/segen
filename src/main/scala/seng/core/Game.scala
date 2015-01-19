package seng.core

import seng.event.Event
import seng.gfx._

abstract class Game(val graphics: Graphics) {
  private var currentScene: Scene = new Scene
  private var events: List[Event] = List.empty

  def getCurrentScene = currentScene
  def setCurrentScene(scene:Scene) = {
    currentScene = scene
  }

  def startingEvents(): List[Event]

  def start(width:Int, height:Int, fullscreen: Boolean = false) = {
    graphics.init(width, height, fullscreen)
    events = startingEvents()

    var frames = 0
    var lastTime = System.currentTimeMillis()

    while(!graphics.shouldCloseWindow()) {
      events = currentScene.update(events)

      val now = System.currentTimeMillis()
      if (lastTime + 2000 < now) {
        // TODO: save FPS value (frames / 2)
        lastTime = now
        frames = 0
      }
      frames += 1

      graphics.clear()
      currentScene.render()

      graphics.update()
    }
  }
}
