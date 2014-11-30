package seng.gfx.lwjgl

import org.lwjgl.opengl._
import org.lwjgl.system.glfw._

import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._
import org.lwjgl.system.glfw.GLFW._

import seng.gfx.Graphics

class LwjglGraphics extends Graphics {
  var window: Long = NULL

  override def init(width: Int, height: Int, fullscreen: Boolean): Unit = {
    glfwSetErrorCallback(ErrorCallback.Util.getDefault)

    if (glfwInit() != GL11.GL_TRUE)
      throw new IllegalStateException("Unable to initialize GLFW")

    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)

    val width = 300
    val height = 300

    window = glfwCreateWindow(width, height, "Seng", NULL, NULL)
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

    GLContext.createFromCurrent()
    glClearColor(0f, 0f, 0f, 1f)


    // TODO: orthogonal drawing
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    glOrtho(0, width, 0, height, 1, -1)
    glMatrixMode(GL_MODELVIEW)
  }

  override def clear() = glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
  override def swapBuffers() = glfwSwapBuffers(window)
  override def handleEvents() = glfwPollEvents()
  override def shouldCloseWindow() = glfwWindowShouldClose(window) == GL_TRUE
}
