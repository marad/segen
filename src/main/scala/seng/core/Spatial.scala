package seng.core

import seng.core.props.{Position, Renderable, Rotation, Scale}

class Spatial(override val id:Long) extends Entity(id) with Renderable {
  val position: Position = new Position(0f, 0f, 0f)
  val rotation: Rotation = new Rotation(0f, 0f, 0f)
  val scale: Scale = new Scale(0f, 0f, 0f)

  def render() = None
}
