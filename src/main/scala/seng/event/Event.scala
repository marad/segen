package seng.event

import seng.core.Entity

trait Event

trait SimpleEvent extends Event {
  def perform: (List[Event])
}

trait EntityEvent extends Event {
  val toEntity: Entity.Id
  def perform(e:Entity) : List[Event]
}

trait GlobalEvent extends Event {
  def perform(entities: Entity.Map): List[Event]
}

