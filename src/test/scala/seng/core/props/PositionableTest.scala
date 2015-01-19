package seng.core.props

class PositionableTest extends org.specs2.mutable.Specification {

  class PositionableObject extends Positionable {
    protected val _position: Position = new Position(1f, 2f)
  }

  "Positionable object" should {
    "move properly" in {
      val positionable = new PositionableObject
      positionable.move(5f, 6f)
      positionable.position.x must be equalTo 6f
      positionable.position.y must be equalTo 8f
    }

    "set position properly" in {
      val positionable = new PositionableObject
      positionable.position(5f, 6f)
      positionable.position.x must be equalTo 5f
      positionable.position.y must be equalTo 6f
    }

  }
}
