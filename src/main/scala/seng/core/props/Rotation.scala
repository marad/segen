package seng.core.props

case class Rotation(var x:Float, var y:Float, var z:Float) {
  def set(x:Float, y:Float, z:Float): Unit = {
    this.x = x
    this.y = y
    this.z = z
  }
}

