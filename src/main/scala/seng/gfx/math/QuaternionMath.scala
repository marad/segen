package seng.gfx.math

import org.lwjgl.util.vector.{Quaternion, Vector3f}

class QuaternionMath(q: Quaternion) {
  def * (r: Quaternion) = Quaternion.mul(q, r, null)
//  def * (r:Quaternion) = {
//    val result = new Quaternion()
//    result.x = q.w*r.x + q.x*r.w + q.y*r.z - q.z*r.y
//    result.y = q.w*r.y - q.x*r.z + q.y*r.w + q.z*r.x
//    result.z = q.w*r.z + q.x*r.y - q.y*r.x + q.z*r.w
//    result.w = q.w*r.w - q.x*r.x - q.y*r.y - q.z*r.z
//    result
//  }

  def !* (r: Quaternion) = Quaternion.mulInverse(q, r, null)
//  def !* (r: Quaternion) = q * r.negate(null)
}

object QuaternionMath {
  implicit def wrapper(q:Quaternion): QuaternionMath = new QuaternionMath(q)

  def fromAngleAndAxis(angle:Float, axis: Vector3f): Quaternion = fromAngleAndAxis(angle, axis.x, axis.y, axis.z)
  def fromAngleAndAxis(angle:Float, x:Float, y:Float, z:Float): Quaternion = {
    val theta = angle / 2d
    val sin = Math.sin(theta)

    val q = new Quaternion()
    q.x = x * sin.toFloat
    q.y = y * sin.toFloat
    q.z = z * sin.toFloat
    q.w = Math.cos(theta).toFloat

    q
  }

  def rotateVectorAroundAxis(angle:Float, axis: Vector3f, toRotate: Vector3f): Unit = {
    val viewQuaternion = new Quaternion(toRotate.x, toRotate.y, toRotate.z, 0)
    val rotationQuaternion = fromAngleAndAxis(angle, axis)

    viewQuaternion.normalise(viewQuaternion)
    rotationQuaternion.normalise(rotationQuaternion)

    val result = rotationQuaternion * viewQuaternion !* rotationQuaternion

    toRotate.x = result.x
    toRotate.y = result.y
    toRotate.z = result.z

    toRotate.normalise(toRotate)
  }
}
