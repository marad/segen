package seng.core.props

trait Rotatable {
  val rotation: Rotation

  def rotate(x: Float, y:Float, z:Float) = {
    rotation.x += x
    rotation.y += y
    rotation.z += z
  }

  def setRotation(x:Float, y:Float, z:Float) = {
    rotation.x = x
    rotation.y = y
    rotation.z = z
  }
}
