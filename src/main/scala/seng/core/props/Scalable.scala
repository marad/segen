package seng.core.props

trait Scalable {
  protected val _scale: Scale
  def scale:Scale = _scale.copy()
  def scale(x:Float, y:Float):Unit = {
    _scale.x = x
    _scale.y = y
    scaleUpdated()
  }

  protected def scaleUpdated(): Unit = {}
}
