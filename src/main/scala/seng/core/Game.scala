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

    var frames = 0
    var lastTime = System.currentTimeMillis()


    val shaderProgram = new ShaderProgram

    shaderProgram.attachShader(new Shader(ShaderType.Vertex, "shaders/basic.vert"))
    shaderProgram.attachShader(new Shader(ShaderType.Fragment, "shaders/basic.frag"))

    shaderProgram.bindAttribute(Mesh.VertexArrayIndex, "position")
    shaderProgram.bindAttribute(Mesh.ColorArrayIndex, "in_color")

    shaderProgram.build()

    camera = new Camera( width.toFloat / height.toFloat, 45f, 0.1f, 100f )
    camera.setPosition(0f, 0f, -50f)

    val projectionMatrixLocation = shaderProgram.getUniformLocation("projMatrix")
    val viewMatrixLocation = shaderProgram.getUniformLocation("viewMatrix")
//    val modelMatrixLocation = shaderProgram.getUniformLocation("modelMatrix")

    val projectionBuffer = BufferUtils.createFloatBuffer(16)
    val viewBuffer = BufferUtils.createFloatBuffer(16)
//    val modelBuffer = BufferUtils.createFloatBuffer(16)


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

      GL20.glUniformMatrix4(projectionMatrixLocation, false, projectionBuffer)
      GL20.glUniformMatrix4(viewMatrixLocation, false, viewBuffer)
//      GL20.glUniformMatrix4(modelMatrixLocation, false, modelBuffer)

      currentScene.render()

      GL20.glUseProgram(0)
      graphics.update()

    }
  }
}
