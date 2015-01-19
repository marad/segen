package seng.core.props

trait Rotatable {
  protected val _rotation: Rotation

  def rotation: Rotation = _rotation.copy()

  def rotation(angle:Float):Unit = {
    _rotation.angle = angle
    rotationUpdated()
  }

  def rotate(angle: Float):Unit = {
    _rotation.angle += angle
    rotationUpdated()
  }

  protected def rotationUpdated(): Unit = {}
}
