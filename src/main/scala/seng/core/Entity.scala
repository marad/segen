package seng.core

import collection.mutable

trait Entity {
  val id: Entity.Id
  val properties: mutable.AnyRefMap[String, String]
}

object Entity {
  type Id = Long
  type Map = mutable.HashMap[Entity.Id, Entity]

  private var lastId: Entity.Id = 0L
  def generateNextId(): Entity.Id = this.synchronized {
    lastId += 1
    lastId
  }
}
