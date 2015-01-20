package seng.core

import seng.core.Entity.Id

import scala.collection.mutable

class SimpleEntity extends Entity {
  val id: Id = Entity.generateNextId()
  val properties = new mutable.AnyRefMap[String, String]()
}
