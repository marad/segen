package seng.core.props

trait Scalable {
  protected val _scale: Scale
  def scale:Scale = scale.copy()
  def scale(x:Float, y:Float, z:Float):Unit = {
    _scale.x = x
    _scale.y = y
    _scale.z = z
    scaleUpdated()
  }

  protected def scaleUpdated(): Unit = {}
}
