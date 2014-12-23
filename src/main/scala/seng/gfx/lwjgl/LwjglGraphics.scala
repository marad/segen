package seng.gfx.lwjgl

import org.lwjgl.opengl.{GL11, Display, DisplayMode}
import org.lwjgl.util.glu.GLU

import seng.gfx.Graphics

class LwjglGraphics extends Graphics {
  override def init(width: Int, height: Int, fullscreen: Boolean): Unit = {
    Display.setDisplayMode(new DisplayMode(width, height))
    Display.setFullscreen(fullscreen)
    Display.setVSyncEnabled(false)



    Display.create()
    GL11.glClearColor(0.2f, 0.2f, 0.2f, 1)
    GL11.glEnable(GL11.GL_DEPTH_TEST)

    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glLoadIdentity()
    //GL11.glOrtho(0, width, 0, height, 1, -1)
    GLU.gluPerspective(80f, width*1f/height, 0.5f, 300f)

    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    GL11.glLoadIdentity()

    GLU.gluLookAt(0f, 10f, -100f, 0f, 0f, 0f, 0f, 1f, 0f)
  }

  override def update(): Unit = Display.update()

  override def clear(): Unit = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

  override def shouldCloseWindow(): Boolean = Display.isCloseRequested
}
