package seng.core.props

case class Rotation(var angle:Float) {
  def set(angle:Float): Unit = {
    this.angle = angle
  }
}

