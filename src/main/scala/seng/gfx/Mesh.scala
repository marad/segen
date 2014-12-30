package seng.gfx

import java.nio.FloatBuffer

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL15, GL11, GL20, GL30}

object Mesh {
  val VertexArrayIndex: Int = 0
  val ColorArrayIndex: Int = 1
  val TexCoordArrayIndex: Int = 2
  val NormalArrayIndex: Int = 3
}

class Mesh(vertexData: Array[Vertex], indices: Array[Int]) {
  val indicesCount = indices.length

  val vaoId = buildVertexArrayObject()
  val vboIndexId = buildIndexBufferObject()

  def render() = {
    GL30.glBindVertexArray(vaoId)
    GL20.glEnableVertexAttribArray(Mesh.VertexArrayIndex)
    GL20.glEnableVertexAttribArray(Mesh.ColorArrayIndex)
    GL20.glEnableVertexAttribArray(Mesh.TexCoordArrayIndex)
    GL20.glEnableVertexAttribArray(Mesh.NormalArrayIndex)

    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndexId)
    GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_INT, 0)

    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    GL20.glDisableVertexAttribArray(Mesh.VertexArrayIndex)
    GL20.glDisableVertexAttribArray(Mesh.ColorArrayIndex)
    GL20.glDisableVertexAttribArray(Mesh.TexCoordArrayIndex)
    GL20.glDisableVertexAttribArray(Mesh.NormalArrayIndex)
    GL30.glBindVertexArray(0)
  }

  private def prepareCompositeBuffer(): FloatBuffer = {
    val buffer = BufferUtils.createFloatBuffer(vertexData.length * Vertex.stride)
    for(v <- vertexData) {
      buffer.put(v.elements)
    }

    buffer.flip()
    buffer
  }

  private def buildVertexArrayObject(): Int = {
    val vaoId = GL30.glGenVertexArrays()
    val vboId = GL15.glGenBuffers()
    GL30.glBindVertexArray(vaoId)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, prepareCompositeBuffer(), GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(Mesh.VertexArrayIndex, Vertex.positionElementCount,
      GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset)
    GL20.glVertexAttribPointer(Mesh.ColorArrayIndex, Vertex.colorElementCount,
      GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset)
    GL20.glVertexAttribPointer(Mesh.TexCoordArrayIndex, Vertex.textureElementCount,
      GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset)
    GL20.glVertexAttribPointer(Mesh.NormalArrayIndex, Vertex.normalElementCount,
      GL11.GL_FLOAT, false, Vertex.stride, Vertex.normalByteOffset)
    GL30.glBindVertexArray(0)

    vaoId
  }

  private def buildIndexBufferObject(): Int = {
    val indexBuffer = BufferUtils.createIntBuffer(indicesCount)
    indexBuffer.put(indices)
    indexBuffer.flip()

    val vboIndexId = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndexId)
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW)
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)

    vboIndexId
  }
}


