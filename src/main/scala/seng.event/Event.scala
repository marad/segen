package seng.core

import seng.core.props.{Rotatable, Positionable}

trait Event

trait EntityEvent extends Event {
  val toEntity: Entity.Id
  def perform(e:Entity) : List[Event]
}

trait GlobalEvent extends Event {
  def perform(entities: Entity.Map): List[Event]
}

trait FromEntityEvent extends Event {
  val fromEntity: Entity.Id
}

case class MoveEvent(toEntity: Entity.Id, x: Float, y:Float, z:Float) extends EntityEvent {
  override def perform(entity: Entity) = {
    entity match {
      case e: Entity with Positionable => e.move(x, y, z)
      case _ =>
    }
    List.empty
  }
}

case class RotateEvent(toEntity: Entity.Id, x:Float, y:Float, z:Float) extends EntityEvent {
  override def perform(entity: Entity) = {
    entity match {
      case e: Entity with Rotatable => e.rotate(x, y, z)
      case _ =>
    }
    List.empty
  }
}

case class AreaEvent() extends GlobalEvent {
  override def perform(entities: Entity.Map) = {
    List.empty
  }
}