package seng.core.props

trait Positionable {
  protected val _position: Position

  def position = _position.copy()

  def position(x:Float, y:Float, z:Float): Unit = {
    _position.x = x
    _position.y = y
    _position.z = z
    positionUpdated()
  }
  def move(x:Float, y:Float, z:Float): Unit = {
    _position.x += x
    _position.y += y
    _position.z += z
    positionUpdated()
  }

  protected def positionUpdated():Unit = {}
}
