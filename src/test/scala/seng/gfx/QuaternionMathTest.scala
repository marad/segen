package seng.gfx

import org.lwjgl.util.vector.{Quaternion, Vector3f}
import org.specs2.mutable.Specification

class QuaternionMathTest extends Specification {

  "QuaterionMath object" should {
    "create quaterion from angle and axis as vector" in {
      val angle = (Math.PI / 2d).toFloat
      val axis = new Vector3f(1, 1, 1)

      val result = QuaternionMath.fromAngleAndAxis(angle, axis)

      result.x must be equalTo 0.70710677f
      result.y must be equalTo 0.70710677f
      result.z must be equalTo 0.70710677f
      result.w must be equalTo 0.70710677f
    }

    "rotate vector around axis" in {
      val angle = (120d * Math.PI / 180d).toFloat
      val vector = new Vector3f(1, 0, 0)
      val axis = new Vector3f(1, 1, 1)

      QuaternionMath.rotateVectorAroundAxis(angle, axis, vector)

      vector.x must be between (-0.3f, 0.3f)
      vector.y must be between (0.8f, 1.2f)
      vector.z must be between (-0.3f, 0.3f)
    }
  }

}
