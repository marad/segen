package seng.gfx.lwjgl

import org.lwjgl.opengl.{GL11, Display, DisplayMode}

import seng.gfx.Graphics

class LwjglGraphics extends Graphics {
  override def init(width: Int, height: Int, fullscreen: Boolean): Unit = {
    Display.setDisplayMode(new DisplayMode(width, height))
    Display.setFullscreen(fullscreen)
    Display.setVSyncEnabled(false)


    Display.create()
    GL11.glClearColor(1, 1, 1, 1)

    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glLoadIdentity()
    GL11.glOrtho(0, width, 0, height, 1, -1)
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
  }

  override def update(): Unit = Display.update()

  override def clear(): Unit = GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

  override def shouldCloseWindow(): Boolean = Display.isCloseRequested
}
