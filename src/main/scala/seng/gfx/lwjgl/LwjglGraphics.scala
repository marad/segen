package seng.gfx.lwjgl

import org.lwjgl.opengl._
import org.lwjgl.util.glu.GLU

import seng.gfx.Graphics

class LwjglGraphics extends Graphics {
  override def init(width: Int, height: Int, fullscreen: Boolean): Unit = {
    val pixelFormat = new PixelFormat()
    val contextAttributes = new ContextAttribs(3, 2)
      .withForwardCompatible(true)
      .withProfileCore(true)

    Display.setDisplayMode(new DisplayMode(width, height))
    Display.setFullscreen(fullscreen)
    Display.setVSyncEnabled(false)
    Display.create(pixelFormat, contextAttributes)


    GL11.glViewport(0, 0, width, height)

    GL11.glClearColor(0.3f, 0.3f, 0.3f, 1)

    GL11.glEnable(GL11.GL_DEPTH_TEST)
//    GL11.glDepthMask(false)
    GL11.glDepthFunc(GL11.GL_LEQUAL)
    GL11.glClearDepth(1.0f)
//    GL11.glDepthRange(0.0, 1.0)
//    GL11.glEnable(GL32.GL_DEPTH_CLAMP)

//    GL11.glEnable(GL11.GL_CULL_FACE)
    GL11.glFrontFace(GL11.GL_CW)
//    GL11.glCullFace(GL11.GL_BACK)

//    GL11.glEnable(GL11.GL_BLEND)
//    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)


//    GL11.glViewport(0, 0, width, height)

//    GL11.glMatrixMode(GL11.GL_PROJECTION)
//    GL11.glLoadIdentity()
    //GL11.glOrtho(0, width, 0, height, 1, -1)
//    GLU.gluPerspective(80f, width*1f/height, 0.5f, 300f)

//    GL11.glMatrixMode(GL11.GL_MODELVIEW)
//    GL11.glLoadIdentity()

//    GLU.gluLookAt(0f, 10f, -100f, 0f, 0f, 0f, 0f, 1f, 0f)

  }

  override def update(): Unit = Display.update()

  override def clear(): Unit = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

  override def shouldCloseWindow(): Boolean = Display.isCloseRequested
}
