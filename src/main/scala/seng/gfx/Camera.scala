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

  private val _position  = new Position(0f, 0f, 0f)
  private val _direction = new Vector3f(0f, 0f, 1f)
  private val _upVector    : Vector3f = new Vector3f(0f, 1f, 0f)
  private val _rightVector : Vector3f = new Vector3f(1f, 0f, 0f)

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
    _viewMatrix.setIdentity()
    _viewMatrix.m00 = _rightVector.x
    _viewMatrix.m01 = _rightVector.y
    _viewMatrix.m02 = _rightVector.z
    _viewMatrix.m10 = _upVector.x
    _viewMatrix.m11 = _upVector.y
    _viewMatrix.m12 = _upVector.z
    _viewMatrix.m20 = -_direction.x
    _viewMatrix.m21 = -_direction.y
    _viewMatrix.m22 = -_direction.z

    _viewMatrix.translate(new Vector3f(-_position.x, -_position.y, -_position.z))
    viewMatrixNeedsUpdate = false
  }

  def position = _position.copy()
  def position(x:Float, y:Float, z:Float): Position = {
    _position.x = x
    _position.y = y
    _position.z = z
    invalidateViewMatrix()
    
    position
  }

  def move(vector:Vector3f): Unit = move(vector.x, vector.y, vector.z)
  def move(x:Float, y:Float, z:Float): Unit = {
    _position.x += x
    _position.y += y
    _position.z += z
    invalidateViewMatrix()
  }

  def moveRelative(vector: Vector3f): Unit = moveRelative(vector.x, vector.y, vector.z)
  def moveRelative(x:Float, y:Float, z:Float): Unit = {
    _position.x += x * _direction.x
    _position.y += y * _direction.y
    _position.z += z * _direction.z
    invalidateViewMatrix()
  }

  def up = new Vector3f(_upVector.x, _upVector.y, _upVector.z)
  def right = new Vector3f(_rightVector.x, _rightVector.y, _rightVector.z)

  def direction = new Vector3f(_direction.x, _direction.y, _direction.z)
  def direction(x:Float, y:Float, z:Float): Vector3f = {
    _direction.x = x
    _direction.y = y
    _direction.z = z
    invalidateViewMatrix()

    direction
  }

  def lookAt(x:Float, y:Float, z:Float): Unit = {
    _direction.x = x - _position.x
    _direction.y = y - _position.y
    _direction.z = z - _position.z
    _direction.normalise(_direction)

    Vector3f.cross(MathHelpers.upVector, _direction, _rightVector).normalise(_rightVector)
    Vector3f.cross(_direction, _rightVector, _upVector).normalise(_upVector)

    invalidateViewMatrix()
  }

  def yaw(yaw:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(yaw, _upVector, _direction)
    Vector3f.cross(_upVector, _direction, _rightVector).normalise(_rightVector)
    invalidateViewMatrix()
  }

  def pitch(pitch:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(pitch, _rightVector, _direction)
    Vector3f.cross(_direction, _rightVector, _upVector).normalise(_upVector)
    invalidateViewMatrix()
  }

  def roll(roll:Float): Unit = {
    QuaternionMath.rotateVectorAroundAxis(roll, _direction, _upVector)
    Vector3f.cross(_upVector, _direction, _rightVector).normalise(_rightVector)
    invalidateViewMatrix()
  }
}

