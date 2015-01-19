package seng.event

import seng.core.Entity
import seng.core.props.Positionable

case class MoveEvent(toEntity: Entity.Id, x: Float, y:Float) extends EntityEvent {
  override def perform(entity: Entity) = {
    entity match {
      case e: Entity with Positionable => e.move(x, y)
      case _ =>
    }
    List.empty
  }
}
