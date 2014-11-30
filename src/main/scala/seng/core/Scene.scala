package seng.core

import seng.core.props.Renderable

class Scene {
  val entities = new Entity.Map()

  def update(eventsToProcess: List[Event]) =
    (for(event <- eventsToProcess) yield {
      event match {
        case e: EntityEvent => entities.get(e.toEntity).map(e.perform).getOrElse(List.empty)
        case e: GlobalEvent => e.perform(entities)
      }
    }).flatten

  def render() = for((_, entity) <- entities) {
    entity match {
      case e: Entity with Renderable => e.render()
      case _ =>
    }
  }
}

