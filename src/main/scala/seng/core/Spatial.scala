package seng.core

import org.lwjgl.opengl.GL11._

import seng.core.props.{Position, Renderable, Rotation, Scale}
import seng.gfx.Mesh

class Spatial extends Entity with Renderable {
  val position: Position = new Position(0f, 0f, 0f)
  val rotation: Rotation = new Rotation(0f, 0f, 0f)
  val scale: Scale = new Scale(1f, 1f, 1f)
  val mesh: Mesh = new Mesh

  def render() = {
    glColor3f(0.5f, 0.5f, 1.0f)
    glPushMatrix()

    glTranslatef(position.x, position.y, position.z)
    glScalef(scale.x, scale.y, scale.z)

    mesh.render()
    glPopMatrix()
  }
}
