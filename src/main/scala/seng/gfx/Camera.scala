package seng.gfx

import org.lwjgl.util.vector.{Quaternion, Matrix4f, Vector3f}
import seng.core.props.Position
import seng.gfx.QuaternionMath.wrapper

import rx.{Rx, Var}

class Camera(aspectArg:Float, fovArg:Float, zNearArg:Float, zFarArg:Float) {
  val fov    = Var(fovArg)
  val aspect = Var(aspectArg)
  val zFar   = Var(zFarArg)
  val zNear  = Var(zNearArg)

  private val _projectionMatrix: Matrix4f = new Matrix4f()
  private val _viewMatrix: Matrix4f = new Matrix4f()

  private val position  = new Position(0f, 0f, 0f)
  private val direction = new Vector3f(0f, 0f, 1f)
  private val upVector    : Vector3f = new Vector3f(0f, 1f, 0f)
  private val rightVector : Vector3f = new Vector3f(1f, 0f, 0f)

  private var viewMatrixNeedsUpdate: Boolean = true

  Rx {
    val h = 1f / Math.tan(fov() * Math.PI.toFloat / 360f).toFloat
    val negDepth = zNear() - zFar()

    _projectionMatrix.setIdentity()
    _projectionMatrix.m00 = h / aspect()
    _projectionMatrix.m11 = h
    _projectionMatrix.m22 = (zFar() + zNear()) / negDepth
    _projectionMatrix.m23 = -1
    _projectionMatrix.m32 = 2 * (zFar() * zNear()) / negDepth
    _projectionMatrix.m33 = 0
  }

  def projectionMatrix: Matrix4f = _projectionMatrix
  def viewMatrix: Matrix4f = {
    if (viewMatrixNeedsUpdate) updateViewMatrix()
    _viewMatrix
  }

  private def invalidateViewMatrix() = viewMatrixNeedsUpdate = true

  def updateViewMatrix(): Unit = {
    Vector3f.cross(MathHelpers.upVector, direction, rightVector).normalise(rightVector)
    Vector3f.cross(direction, rightVector, upVector).normalise(upVector)

    _viewMatrix.setIdentity()
    _viewMatrix.m00 = rightVector.x
    _viewMatrix.m01 = rightVector.y
    _viewMatrix.m02 = rightVector.z
    _viewMatrix.m10 = upVector.x
    _viewMatrix.m11 = upVector.y
    _viewMatrix.m12 = upVector.z
    _viewMatrix.m20 = -direction.x
    _viewMatrix.m21 = -direction.y
    _viewMatrix.m22 = -direction.z

    _viewMatrix.translate(new Vector3f(-position.x, -position.y, -position.z))
    viewMatrixNeedsUpdate = false
  }

  def getPosition = position.copy()
  def setPosition(x:Float, y:Float, z:Float): Unit = {
    position.x = x
    position.y = y
    position.z = z
    invalidateViewMatrix()
  }

  def move(vector:Vector3f): Unit = move(vector.x, vector.y, vector.z)
  def move(x:Float, y:Float, z:Float): Unit = {
    position.x += x
    position.y += y
    position.z += z
    invalidateViewMatrix()
  }


  def getUp = new Vector3f(upVector.x, upVector.y, upVector.z)
  def getRight = new Vector3f(rightVector.x, rightVector.y, rightVector.z)

  def getDirection = new Vector3f(direction.x, direction.y, direction.z)
  def setDirection(x:Float, y:Float, z:Float): Unit = {
    direction.x = x
    direction.y = y
    direction.z = z
    updateViewMatrix()
  }

  def lookAt(x:Float, y:Float, z:Float): Unit = {
    direction.x = x - position.x
    direction.y = y - position.y
    direction.z = z - position.z
    direction.normalise(direction)
    invalidateViewMatrix()
  }

  def yaw(yaw:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(yaw, MathHelpers.upVector, direction)
    invalidateViewMatrix()
  }

  def pitch(pitch:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(pitch, rightVector, direction)
    println(direction)
    invalidateViewMatrix()
  }

  def roll(roll:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(roll, direction, upVector)
    invalidateViewMatrix()
  }
}

