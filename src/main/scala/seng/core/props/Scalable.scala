package seng.core.props

trait Scalable {
  val scale: Scale

  def setScale(x:Float, y:Float, z:Float) = {
    scale.x = x
    scale.y = y
    scale.z = z
  }
}
