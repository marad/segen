package seng.core.props

trait Rotatable {
  protected val _rotation: Rotation

  def rotation: Rotation = _rotation.copy()

  def rotation(x:Float, y:Float, z:Float):Unit = {
    _rotation.x = x
    _rotation.y = y
    _rotation.z = z
    rotationUpdated()
  }

  def rotate(x: Float, y:Float, z:Float):Unit = {
    _rotation.x += x
    _rotation.y += y
    _rotation.z += z
    rotationUpdated()
  }

  protected def rotationUpdated(): Unit = {}
}
