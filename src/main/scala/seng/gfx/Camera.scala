package seng.gfx

import org.lwjgl.util.vector.{Quaternion, Matrix4f, Vector3f}
import seng.core.props.Position
import seng.gfx.QuaternionMath.wrapper

import rx.{Rx, Var}

class Camera(aspectArg:Float, fovArg:Float, zFarArg:Float, zNearArg:Float) {
  val fov    = Var(fovArg)
  val aspect = Var(aspectArg)
  val zFar   = Var(zFarArg)
  val zNear  = Var(zNearArg)


  val projectionMatrix: Matrix4f = new Matrix4f()
  val viewMatrix: Matrix4f = new Matrix4f()

  private val position  = new Position(0f, 0f, 0f)
  private val direction = new Vector3f(0f, 0f, 1f)
  private val upVector    : Vector3f = new Vector3f(0f, 1f, 0f)

  private val rightVector : Vector3f = new Vector3f(1f, 0f, 0f)


  Rx {
    val h = 1f / Math.tan(fov() * Math.PI.toFloat / 360f).toFloat
    val negDepth = zNear() - zFar()

    projectionMatrix.m00 = h / aspect()
    projectionMatrix.m11 = h
    projectionMatrix.m22 = (zFar() + zNear()) / negDepth
    projectionMatrix.m23 = -1
    projectionMatrix.m32 = 2 * (zFar() * zNear()) / negDepth
    projectionMatrix.m33 = 0
  }

  def updateViewMatrix(): Unit = {
//    direction.set(0, 0, 1)
//    rotateVectorAroundAxis(-yaw, MathHelpers.upVector, direction)
//    rotateVectorAroundAxis(pitch, MathHelpers.rightVector, direction)


    direction.normalise(direction)
    Vector3f.cross(MathHelpers.upVector, direction, rightVector).normalise(rightVector)
    Vector3f.cross(direction, rightVector, upVector).normalise(upVector)

    println(direction)
    println(rightVector)
    println(upVector)

    viewMatrix.setIdentity()
    viewMatrix.m00 = rightVector.x
    viewMatrix.m01 = rightVector.y
    viewMatrix.m02 = rightVector.z
    viewMatrix.m10 = upVector.x
    viewMatrix.m11 = upVector.y
    viewMatrix.m12 = upVector.z
    viewMatrix.m20 = -direction.x
    viewMatrix.m21 = -direction.y
    viewMatrix.m22 = -direction.z

//    viewMatrix.m30 = position.x
//    viewMatrix.m31 = position.y
//    viewMatrix.m32 = position.z

    viewMatrix.translate(new Vector3f(-position.x, -position.y, -position.z))
  }

  def getPosition = position.copy()
  def setPosition(x:Float, y:Float, z:Float): Unit = {
    position.x = x
    position.y = y
    position.z = z
    updateViewMatrix()
  }

  def move(vector:Vector3f): Unit = move(vector.x, vector.y, vector.z)
  def move(x:Float, y:Float, z:Float): Unit = {
    position.x += x
    position.y += y
    position.z += z
    updateViewMatrix()
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
    updateViewMatrix()
  }


  def rotateVectorAroundAxis(angle:Float, axis:Vector3f, toRotate:Vector3f): Unit = {
    val viewQuaternion = new Quaternion(toRotate.x, toRotate.y, toRotate.z, 0)
    val rotationQuaternion = QuaternionMath.fromAngleAndAxis(angle, axis)

    val result = rotationQuaternion * viewQuaternion !* rotationQuaternion
    toRotate.x = result.x
    toRotate.y = result.y
    toRotate.z = result.z
    updateViewMatrix()
  }

  def addYaw(yaw:Float): Unit = rotateVectorAroundAxis(yaw, upVector, direction)
  def addPitch(pitch:Float): Unit = rotateVectorAroundAxis(pitch, rightVector, direction)
  def addRoll(roll:Float): Unit = rotateVectorAroundAxis(roll, direction, upVector)

  def rotateCamera(angle:Float, axis:Vector3f) = rotateVectorAroundAxis(angle, axis, direction)
}

