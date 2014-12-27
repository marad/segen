package seng.gfx

import org.lwjgl.util.vector.{Vector3f, Quaternion}

class QuaternionMath(q: Quaternion) {
  def * (r: Quaternion) = Quaternion.mul(q, r, null)
//  def * (r:Quaternion) = {
//    val result = new Quaternion()
//    result.x = q.x*r.x - q.y*r.y - q.z*r.z - q.w*r.w
//    result.y = q.x*r.y + q.y*r.x - q.z*r.w + q.w*r.z
//    result.z = q.x*r.z + q.y*r.w + q.z*r.x - q.w*r.y
//    result.w = q.x*r.w - q.y*r.z + q.z*r.y + q.w*r.x
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
    q.x = (x.toDouble * sin).toFloat
    q.y = (y.toDouble * sin).toFloat
    q.z = (z.toDouble * sin).toFloat
    q.w = Math.cos(theta).toFloat

    q
  }

  def rotateVectorAroundAxis(angle:Float, axis: Vector3f, toRotate: Vector3f): Unit = {
    val viewQuaternion = new Quaternion(toRotate.x, toRotate.y, toRotate.z, 1)
    val rotationQuaternion = fromAngleAndAxis(angle, axis)

    viewQuaternion.normalise(viewQuaternion)
    rotationQuaternion.normalise(rotationQuaternion)

    val result = rotationQuaternion * viewQuaternion !* rotationQuaternion
//    val result = Quaternion.mul(
//      Quaternion.mul(rotationQuaternion, viewQuaternion, null),
//      rotationQuaternion.negate(null), null
//    )
    toRotate.x = result.x
    toRotate.y = result.y
    toRotate.z = result.z

    toRotate.normalise(toRotate)
  }
}
