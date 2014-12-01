package seng.core

import seng.event._
import seng.core.props.Renderable

class Scene {
  val entities = new Entity.Map()

  def update(eventsToProcess: List[Event]) =
    (for(event <- eventsToProcess) yield {
      event match {
        case e: EntityEvent => entities.get(e.toEntity).map(e.perform).getOrElse(List.empty)
        case e: SimpleEvent => e.perform
        case e: GlobalEvent => e.perform(entities)
        case e: CreationEvent =>
          val (newEntities, events) = e.perform
          newEntities.foreach { entity: Entity =>
            entities.put(entity.id, entity)
          }
          events
      }
    }).flatten

  def render() = for((_, entity) <- entities) {
    entity match {
      case e: Entity with Renderable => e.render()
      case _ =>
    }
  }
}

