package seng.core.props

import org.specs2.mutable.Specification

class RotatableTest extends Specification {

  class RotatableObject extends Rotatable {
    val rotation: Rotation = new Rotation(1f, 2f, 3f)
  }

  "Rotatable object" should {
    "rotate properly" in {
      val o = new RotatableObject
      o.rotate(1f, 2f, 3f)
      o.rotation.x must be equalTo 2f
      o.rotation.y must be equalTo 4f
      o.rotation.z must be equalTo 6f
    }

    "set rotation properly" in {
      val o = new RotatableObject
      o.setRotation(5f, 6f, 7f)
      o.rotation.x must be equalTo 5f
      o.rotation.y must be equalTo 6f
      o.rotation.z must be equalTo 7f
    }
  }

}
