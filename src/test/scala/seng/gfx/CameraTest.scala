package seng.gfx

import org.lwjgl.util.vector.{Quaternion, Vector3f}
import org.specs2.mutable.Specification

class CameraTest extends Specification {

  val cameraUnderTest = new Camera(800f/600f, 70f, 1f, 100f)

  "Camera" should {
  }
}
