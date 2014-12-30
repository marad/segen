package seng.gfx.lwjgl

import java.nio.FloatBuffer

import org.lwjgl.opengl.{GL11, GL20}
import org.lwjgl.util.glu.GLU
import org.lwjgl.util.vector.Matrix4f
import seng.gfx.lwjgl.ShaderType.ShaderType
import scala.collection.mutable
import scala.io.Source

object ShaderType {
  type ShaderType = Int
  val Vertex: ShaderType = GL20.GL_VERTEX_SHADER
  val Fragment: ShaderType = GL20.GL_FRAGMENT_SHADER
}

case class Shader(shaderType: ShaderType, resourcePath: String) {
  val shaderId = GL20.glCreateShader(shaderType)
  load()

  private def load(): Unit = {
    val stream = getClass.getClassLoader.getResourceAsStream(resourcePath)
    val source = Source.fromInputStream(stream).mkString

    GL20.glShaderSource(shaderId, source)
    GL20.glCompileShader(shaderId)
    if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      System.err.println("Error: '" + GL20.glGetShaderInfoLog(shaderId, 500) + "'")
    }
  }
  
  def delete(): Unit = {
    GL20.glDeleteShader(shaderId)
  }
  
  override def finalize(): Unit = {
    try {
      delete()
    } finally {
      super.finalize()
    }
  }
}

class ShaderProgram {
  val programId = GL20.glCreateProgram()
  val attachedShaders = new mutable.MutableList[Shader]
  val uniformMatrices4 = new mutable.HashMap[Int, FloatBuffer]

  def attachShader(shader: Shader): Unit = {
    GL20.glAttachShader(programId, shader.shaderId)
    attachedShaders += shader
  }

  def bindAttribute(index: Int, name: String): Unit = {
    GL20.glBindAttribLocation(programId, index, name)
  }

  def build(): Unit = {
    GL20.glLinkProgram(programId)
    GL20.glValidateProgram(programId)

    val errorCheckValue = GL11.glGetError()
    if (errorCheckValue != GL11.GL_NO_ERROR) {
      System.err.println("Shader program validation error: " + GLU.gluErrorString(errorCheckValue))
    }
  }

  def use(): Unit = {
    GL20.glUseProgram(programId)

    for ((location, buffer) <- uniformMatrices4) {
      GL20.glUniformMatrix4(location, false, buffer)
    }
  }

  def delete(): Unit = {
    GL20.glDeleteProgram(programId)
  }

  def bindUniformMatrix4Buffer(name: String, matrixBuffer: FloatBuffer) = {
    val location = GL20.glGetUniformLocation(programId, name)
    uniformMatrices4(location) = matrixBuffer
  }

  override def finalize(): Unit = {
    try {
      delete()
    } finally {
      super.finalize()
    }

  }
}
