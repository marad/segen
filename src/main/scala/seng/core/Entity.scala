package seng.core

import collection.mutable

class Entity {
  val id: Entity.Id = Entity.generateNextId()
  val properties = new mutable.AnyRefMap[String, String]()
}

object Entity {
  type Id = Long
  type Map = mutable.HashMap[Entity.Id, Entity]

  private var lastId: Entity.Id = 0L
  def generateNextId(): Entity.Id = {
    lastId += 1
    return lastId
  }
}
