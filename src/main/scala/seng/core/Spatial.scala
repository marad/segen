package seng.core

import seng.core.props.{Position, Renderable, Rotation, Scale}
import seng.gfx.{Quad, Mesh, Box}

class Spatial(var mesh: Mesh) extends Entity with Renderable {
  val position: Position = new Position(0f, 0f, 0f)
  val rotation: Rotation = new Rotation(0f, 0f, 0f)
  val scale: Scale = new Scale(1f, 1f, 1f)

  def render() = {
//    glPushMatrix()

//    glTranslatef(position.x, position.y, position.z)
//    glRotatef(rotation.x, 1f, 0f, 0f)
//    glRotatef(rotation.y, 0f, 1f, 0f)
//    glRotatef(rotation.z, 0f, 0f, 1f)
//    glScalef(scale.x, scale.y, scale.z)

    if (mesh != null) {
      mesh.render()
    }
//    glPopMatrix()
  }
}
