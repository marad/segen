package seng.core

import org.lwjgl.opengl.GL11._

import seng.core.props.{Position, Renderable, Rotation, Scale}
import seng.gfx.{Quad, Mesh, Box}

class Spatial extends Entity with Renderable {
  val position: Position = new Position(0f, 0f, 0f)
  val rotation: Rotation = new Rotation(0f, 0f, 0f)
  val scale: Scale = new Scale(1f, 1f, 1f)
  val mesh: Mesh = new Box(20f)
//  val mesh: Mesh = new Quad(20f)

  def render() = {
    glColor3f(0.5f, 0.5f, 1.0f)
    glPushMatrix()

    glTranslatef(position.x, position.y, position.z)
    glRotatef(rotation.x, 1f, 0f, 0f)
    glRotatef(rotation.y, 0f, 1f, 0f)
    glRotatef(rotation.z, 0f, 0f, 1f)
    glScalef(scale.x, scale.y, scale.z)

    mesh.render()
    glPopMatrix()
  }
}
