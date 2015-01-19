package seng.core.props

import org.specs2.mutable.Specification

class ScalableTest extends Specification {

  class ScalableObject extends Scalable {
    protected val _scale: Scale = new Scale(1f, 2f)
  }

  "Scalable object" should {
    "set scale properly" in {
      val o = new ScalableObject
      o.scale(4f, 5f)
      o.scale.x must be equalTo 4f
      o.scale.y must be equalTo 5f
    }
  }

}
