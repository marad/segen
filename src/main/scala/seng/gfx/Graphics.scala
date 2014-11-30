package seng.gfx

trait Graphics {
  def init(width: Int, height: Int, fullscreen: Boolean = false): Unit
  def clear(): Unit
  def swapBuffers(): Unit
  def handleEvents(): Unit
  def shouldCloseWindow(): Boolean
}
