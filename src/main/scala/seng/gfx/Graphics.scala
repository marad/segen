package seng.gfx

trait Graphics {
  def init(width: Int, height: Int, fullscreen: Boolean = false): Unit
  def update(): Unit
  def clear(): Unit
  def shouldCloseWindow(): Boolean
}
