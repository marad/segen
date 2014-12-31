package seng.core

import java.nio.FloatBuffer

import org.lwjgl.BufferUtils
import org.lwjgl.util.vector.Matrix4f
import seng.core.props.{Position, Renderable, Rotation, Scale}
import seng.gfx.{MathHelpers, Mesh}

class Spatial(val mesh: Mesh) extends Entity with Renderable {
  private var modelMatrixNeedsUpdate: Boolean = true

  protected val _position: Position = new Position(0f, 0f, 0f)
  protected val _rotation: Rotation = new Rotation(0f, 0f, 0f)
  protected val _scale: Scale = new Scale(1f, 1f, 1f)

  private val _modelMatrix = new Matrix4f()
  private val _modelMatrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

  def modelMatrix:Matrix4f = {
    if (modelMatrixNeedsUpdate) updateModelMatrix()
    _modelMatrix
  }

  def modelMatrixBuffer:FloatBuffer = {
    if (modelMatrixNeedsUpdate) updateModelMatrix()
    _modelMatrixBuffer
  }

  protected override def positionUpdated() = invalidateModelMatrix()
  protected override def rotationUpdated() = invalidateModelMatrix()
  protected override def scaleUpdated() = invalidateModelMatrix()

  def render() = {
    mesh.render()
  }

  private def invalidateModelMatrix(): Unit = modelMatrixNeedsUpdate = true
  private def updateModelMatrix(): Unit = {
    _modelMatrix.setIdentity()
    _modelMatrix.scale(scale.asVector)
    _modelMatrix.rotate(_rotation.z, MathHelpers.forwardVector)
    _modelMatrix.rotate(_rotation.y, MathHelpers.rightVector)
    _modelMatrix.rotate(_rotation.x, MathHelpers.upVector)
    _modelMatrix.translate(position.asVector)

    _modelMatrix.store(_modelMatrixBuffer)
    _modelMatrixBuffer.flip()

    modelMatrixNeedsUpdate = false
  }
}
