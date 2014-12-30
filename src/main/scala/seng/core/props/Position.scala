package seng.core.props

import org.lwjgl.util.vector.Vector3f

case class Position(var x:Float, var y:Float, var z:Float) {
  def asVector = new Vector3f(x, y, z)
}

