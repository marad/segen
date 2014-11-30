package seng.core.props

class PositionableTest extends org.specs2.mutable.Specification {

  class PositionableObject extends Positionable {
    val position: Position = new Position(1f, 2f, 3f)
  }

  "Positionable object" should {
    "move properly" in {
      val positionable = new PositionableObject
      positionable.move(5f, 6f, 7f)
      positionable.position.x must be equalTo 6f
      positionable.position.y must be equalTo 8f
      positionable.position.z must be equalTo 10f
    }

    "set position properly" in {
      val positionable = new PositionableObject
      positionable.setPosition(5f, 6f, 7f)
      positionable.position.x must be equalTo 5f
      positionable.position.y must be equalTo 6f
      positionable.position.z must be equalTo 7f
    }
  }
}
