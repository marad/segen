package seng.gfx.math

import org.lwjgl.util.vector.Vector3f

class VectorMath(v: Vector3f) {

  def unary_- () = v.negate(null)

  def + (w: Vector3f) = Vector3f.add(v, w, null)
  def - (w: Vector3f) = Vector3f.sub(v, w, null)
  def * (w: Vector3f) = Vector3f.cross(v, w, null)

  def + (s: Float) = new Vector3f( v.x + s, v.y + s, v.z + s )
  def - (s: Float) = new Vector3f( v.x - s, v.y - s, v.z - s )
  def * (s: Float) = new Vector3f( v.x * s, v.y * s, v.z * s )
  def / (s: Float) = new Vector3f( v.x / s, v.y / s, v.z / s )
}

object VectorMath {
  implicit def wrapper(v:Vector3f): VectorMath = new VectorMath(v)
}
