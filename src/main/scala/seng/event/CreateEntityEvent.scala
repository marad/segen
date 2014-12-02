package seng.event

import seng.core.Entity

case class CreateEntityEvent(e:Entity) extends GlobalEvent {
  def perform(entities: Entity.Map) = {
    entities.put(e.id, e)
    List.empty
  }
}
