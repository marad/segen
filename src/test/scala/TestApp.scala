import org.lwjgl.Sys
import org.lwjgl.opengl._
import org.lwjgl.system.glfw._

import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._
import org.lwjgl.system.glfw.GLFW._

class TestApp {
  var window:Long = NULL

  def execute(): Unit = {
    try {
      init()
      loop()
      glfwDestroyWindow(window)
    } finally {
      glfwTerminate()
    }
  }

  def init(): Unit = {
    glfwSetErrorCallback(ErrorCallback.Util.getDefault)

    if (glfwInit() != GL11.GL_TRUE)
      throw new IllegalStateException("Unable to initialize GLFW")

    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)

    val width = 300
    val height = 300

    window = glfwCreateWindow(width, height, "Hello World", NULL, NULL)
    if (window == NULL)
      throw new RuntimeException("Failed to create GLFW window")

    WindowCallback.set(window, new WindowCallbackAdapter {
      override def key(window: Long, key:Int, scanCode:Int, action:Int, mods:Int) = {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
          glfwSetWindowShouldClose(window, GL_TRUE)
      }
    })

    val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
    glfwSetWindowPos(
      window,
      (GLFWvidmode.width(vidmode) - width) / 2,
      (GLFWvidmode.height(vidmode) - height) / 2
    )

    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)

    glfwShowWindow(window)
  }

  def loop(): Unit = {
    GLContext.createFromCurrent()

    glClearColor(1f, 0f, 0f, 0f)

    var lastTime: Long = System.currentTimeMillis()
    var frames = 0

    while(glfwWindowShouldClose(window) == GL_FALSE) {

      val now = System.currentTimeMillis()
      if (lastTime + 1000 < now) {
        glfwSetWindowTitle(window, "FPS: " + frames)
        println("FPS: " + frames)
        frames = 0
        lastTime = now
      }

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

      glfwSwapBuffers(window)
      glfwPollEvents()
      frames += 1
    }
  }
}

object TestApp {

  def main(args: Array[String]): Unit = {
    (new TestApp).execute()
  }
}
