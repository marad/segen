package seng.gfx

import org.lwjgl.util.vector.{Vector3f}
import org.specs2.mutable.Specification

import scala.util.Random

class CameraTest extends Specification {


  "Camera" should {
    "start by looking towards +z axis" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)
      camera.direction  should be equalTo MathHelpers.forwardVector
      camera.up         should be equalTo MathHelpers.upVector
      camera.right      should be equalTo MathHelpers.rightVector
    }

    "pitch" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)
      camera.pitch(Math.PI.toFloat / 2f)
      camera.direction  should be equalTo new Vector3f(0, -1, 0)
      camera.up         should be equalTo new Vector3f(0, 0, 1)
      camera.right      should be equalTo new Vector3f(1, 0, 0)
    }

    "yaw" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)
      camera.yaw(Math.PI.toFloat / 2f)
      camera.direction  should be equalTo new Vector3f(1, 0, 0)
      camera.up         should be equalTo new Vector3f(0, 1, 0)
      camera.right      should be equalTo new Vector3f(0, 0, -1)
    }

    "roll" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)
      camera.roll(Math.PI.toFloat / 2f)
      camera.direction  should be equalTo new Vector3f(0, 0, 1)
      camera.up         should be equalTo new Vector3f(-1, 0, 0)
      camera.right      should be equalTo new Vector3f(0, 1, 0)
    }

    "camera up, forward and right vectors stays perpendicular to each other" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)

      camera.yaw(10f)
      camera.pitch(5f)
      camera.roll(12f)

      Vector3f.dot(camera.direction, camera.right) should be between (-0.001f, 0.001f)
      Vector3f.dot(camera.direction, camera.up) should be between (-0.001f, 0.001f)
      Vector3f.dot(camera.up, camera.right) should be between (-0.001f, 0.001f)
    }

    "camera up, forward and right vectors are perpendicular after lookAt()" in {
      val camera = new Camera(800f/600f, 70f, 1f, 100f)

      camera.position(10, 10, 10)
      camera.lookAt(1, 1, 1)

      Vector3f.dot(camera.direction, camera.right) should be between (-0.001f, 0.001f)
      Vector3f.dot(camera.direction, camera.up) should be between (-0.001f, 0.001f)
      Vector3f.dot(camera.up, camera.right) should be between (-0.001f, 0.001f)
    }
  }
}
