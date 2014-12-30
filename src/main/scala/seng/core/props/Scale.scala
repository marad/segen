package seng.core.props

import org.lwjgl.util.vector.Vector3f

case class Scale(var x:Float, var y:Float, var z:Float) {
  def asVector = new Vector3f(x, y, z)
  def set(x:Float, y:Float, z:Float) = {
    this.x = x
    this.y = y
    this.z = z
  }
}

