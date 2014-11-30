package seng.core

import collection.mutable

class Entity(val id: Entity.Id) {
  val properties = new mutable.AnyRefMap[String, String]()
}

object Entity {
  type Id = Long
  type Map = mutable.HashMap[Entity.Id, Entity]
}
