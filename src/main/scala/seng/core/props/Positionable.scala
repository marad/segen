package seng.core.props

trait Positionable {
  val position: Position

  def move(x:Float, y:Float, z:Float) = {
    position.x += x
    position.y += y
    position.z += z
  }

  def getPosition = position.copy()
  def setPosition(x:Float, y:Float, z:Float) = {
    position.x = x
    position.y = y
    position.z = z
  }
}
