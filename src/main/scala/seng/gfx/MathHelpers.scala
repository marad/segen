package seng.gfx

import org.lwjgl.util.vector.Vector3f

object MathHelpers {
  def coTangent(angle: Float) = (1f / Math.tan(angle)).toFloat
  def degToRad(degrees: Float) = (degrees * (Math.PI / 180d)).toFloat

  val rightVector   = new Vector3f(1, 0, 0)
  val upVector      = new Vector3f(0, 1, 0)
  val forwardVector = new Vector3f(0, 0, 1)
}
