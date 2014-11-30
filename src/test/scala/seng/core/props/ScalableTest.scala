package seng.core.props

import org.specs2.mutable.Specification

class ScalableTest extends Specification {

  class ScalableObject extends Scalable {
    val scale: Scale = new Scale(1f, 2f, 3f)
  }

  "Scalable object" should {
    "set scale properly" in {
      val o = new ScalableObject
      o.setScale(4f, 5f, 6f)
      o.scale.x must be equalTo 4f
      o.scale.y must be equalTo 5f
      o.scale.z must be equalTo 6f
    }
  }

}
