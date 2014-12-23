package seng.gfx

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL15, GL11, GL20, GL30}

class Mesh(vertices: Array[Float], indices: Array[Int]) {
  val verticesCount = vertices.length
  val indicesCount = indices.length

  val vertexBuffer = BufferUtils.createFloatBuffer(verticesCount)
  vertexBuffer.put(vertices)
  vertexBuffer.flip()

  val indexBuffer = BufferUtils.createIntBuffer(indicesCount)
  indexBuffer.put(indices)
  indexBuffer.flip()

  val vaoId = GL30.glGenVertexArrays()
  GL30.glBindVertexArray(vaoId)

  val vboVertexId = GL15.glGenBuffers()
  GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexId)
  GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW)
  GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0)
  GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
  GL30.glBindVertexArray(0)

  val vboIndexId = GL15.glGenBuffers()
  GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndexId)
  GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW)
  GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)


  def render() = {
    GL30.glBindVertexArray(vaoId)
    GL20.glEnableVertexAttribArray(0)

    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndexId)
    GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_INT, 0)

    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    GL20.glDisableVertexAttribArray(0)
    GL30.glBindVertexArray(0)
  }
}
