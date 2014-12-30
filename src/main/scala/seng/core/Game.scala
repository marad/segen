package seng.core

import org.lwjgl.BufferUtils
import org.lwjgl.opengl._
import org.lwjgl.util.glu.GLU
import org.lwjgl.util.vector.Matrix4f
import org.newdawn.slick.Color
import org.newdawn.slick.opengl.{TextureLoader, Texture}
import org.newdawn.slick.util.ResourceLoader
import seng.core.props.{Rotation, Position}
import seng.event.Event
import seng.gfx._
import seng.gfx.lwjgl.{ShaderType, Shader, ShaderProgram}
import util.{Camera => DustinCam}

abstract class Game(val graphics: Graphics) {
  private var currentScene: Scene = new Scene
  private var events: List[Event] = List.empty

  var camera: Camera = null

  def getCurrentScene = currentScene
  def setCurrentScene(scene:Scene) = {
    currentScene = scene
  }

  def startingEvents(): List[Event]

  def start(width:Int, height:Int, fullscreen: Boolean = false) = {
    graphics.init(width, height, fullscreen)

    events = startingEvents()

    camera = new Camera( width.toFloat / height.toFloat, 45f, 0.1f, 100f )
    camera.position(0f, 0f, -50f)

    var frames = 0
    var lastTime = System.currentTimeMillis()


    val shaderProgram = new ShaderProgram

    shaderProgram.attachShader(new Shader(ShaderType.Vertex, "shaders/basic.vert"))
    shaderProgram.attachShader(new Shader(ShaderType.Fragment, "shaders/basic.frag"))

    shaderProgram.bindAttribute(Mesh.VertexArrayIndex, "in_position")
    shaderProgram.bindAttribute(Mesh.ColorArrayIndex, "in_color")
    shaderProgram.bindAttribute(Mesh.TexCoordArrayIndex, "in_texCoord")
    shaderProgram.bindAttribute(Mesh.NormalArrayIndex, "in_normal")

    shaderProgram.build()

    val projectionBuffer = BufferUtils.createFloatBuffer(16)
    val viewBuffer = BufferUtils.createFloatBuffer(16)
//    val modelBuffer = BufferUtils.createFloatBuffer(16)

    shaderProgram.bindUniformMatrix4Buffer("projMatrix", projectionBuffer)
    shaderProgram.bindUniformMatrix4Buffer("viewMatrix", viewBuffer)


    val texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("avatar.png"))

    while(!graphics.shouldCloseWindow()) {
      events = currentScene.update(events)

      val now = System.currentTimeMillis()
      if (lastTime + 2000 < now) {
        Display.setTitle("FPS: " + (frames / 2))
        lastTime = now
        frames = 0
      }
      frames += 1

      graphics.clear()
      shaderProgram.use()

      camera.projectionMatrix.store(projectionBuffer); projectionBuffer.flip()
      camera.viewMatrix.store(viewBuffer); viewBuffer.flip()

      currentScene.render()

      GL20.glUseProgram(0)
      graphics.update()

    }
  }
}
