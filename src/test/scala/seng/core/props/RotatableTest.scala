package seng.core.props

import org.specs2.mutable.Specification

class RotatableTest extends Specification {

  class RotatableObject extends Rotatable {
    protected val _rotation: Rotation = new Rotation(1f)
  }

  "Rotatable object" should {
    "rotate properly" in {
      val o = new RotatableObject
      o.rotate(1f)
      o.rotation.angle must be equalTo 2f
    }

    "set rotation properly" in {
      val o = new RotatableObject
      o.rotation(5f)
      o.rotation.angle must be equalTo 5f
    }
  }

}
