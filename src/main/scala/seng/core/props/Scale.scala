package seng.core.props

case class Scale(var x:Float, var y:Float) {
  def set(x:Float, y:Float) = {
    this.x = x
    this.y = y
  }
}

