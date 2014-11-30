package seng.core

import org.lwjgl.opengl.GL11._

import seng.core.props.{Position, Renderable, Rotation, Scale}

class Spatial extends Entity with Renderable {
  val position: Position = new Position(0f, 0f, 0f)
  val rotation: Rotation = new Rotation(0f, 0f, 0f)
  val scale: Scale = new Scale(1f, 1f, 1f)

  def render() = {
    glColor3f(0.5f, 0.5f, 1.0f)
    glPushMatrix()

    glTranslatef(position.x, position.y, position.z)
    glScalef(scale.x, scale.y, scale.z)

    glBegin(GL_QUADS)
    glVertex2f(0, 0)
    glVertex2f(0, 100)
    glVertex2f(100, 100)
    glVertex2f(100, 0)
    glEnd()
    glPopMatrix()
  }
}
