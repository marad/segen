package seng.event

import seng.core.Entity
import seng.core.props.Rotatable

case class RotateEvent(toEntity: Entity.Id, angle:Float) extends EntityEvent {
  override def perform(entity: Entity) = {
    entity match {
      case e: Entity with Rotatable => e.rotate(angle)
      case _ =>
    }
    List.empty
  }
}
