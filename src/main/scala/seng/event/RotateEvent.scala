package seng.event

import seng.core.Entity
import seng.core.props.Rotatable

case class RotateEvent(toEntity: Entity.Id, x:Float, y:Float, z:Float) extends EntityEvent {
  override def perform(entity: Entity) = {
    entity match {
      case e: Entity with Rotatable => e.rotate(x, y, z)
      case _ =>
    }
    List.empty
  }
}
