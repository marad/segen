package seng.gfx

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL30._

class Mesh {
  val vaoId = glGenVertexArrays()
  val vboId = glGenBuffers()

  glBindVertexArray(vaoId)
  glBindBuffer(GL_ARRAY_BUFFER, vboId)


  def render() = {
    glBegin(GL_QUADS)
    glVertex2f(0, 0)
    glVertex2f(0, 100)
    glVertex2f(100, 100)
    glVertex2f(100, 0)
    glEnd()
  }
}
