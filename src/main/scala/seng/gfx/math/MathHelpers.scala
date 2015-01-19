package seng.gfx.math

object MathHelpers {
  def coTangent(angle: Float) = (1f / Math.tan(angle)).toFloat
  def degToRad(degrees: Float) = (degrees * (Math.PI / 180d)).toFloat
}
