package seng.core.props

trait Positionable {
  protected val _position: Position

  def position = _position.copy()

  def position(x:Float, y:Float): Unit = {
    _position.x = x
    _position.y = y
    positionUpdated()
  }
  def move(x:Float, y:Float): Unit = {
    _position.x += x
    _position.y += y
    positionUpdated()
  }

  protected def positionUpdated():Unit = {}
}
