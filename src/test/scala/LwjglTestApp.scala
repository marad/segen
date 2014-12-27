import org.lwjgl.BufferUtils
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.util.vector.{Vector3f, Matrix4f}
import seng.gfx.{Box, Quad, Camera}
import seng.gfx.lwjgl.{ShaderType, Shader, ShaderProgram}

object LwjglTestApp {

  val width = 800
  val height = 600

  def setupOpenGL(): Unit = {
    val pixelFormat = new PixelFormat()
      .withBitsPerPixel(32)
      .withDepthBits(16)

    val contextAttributes = new ContextAttribs(3, 2)
      .withForwardCompatible(true)
      .withProfileCore(true)

    Display.setDisplayMode(new DisplayMode(width, height))
    Display.setFullscreen(false)
    Display.setVSyncEnabled(false)
    Display.create(pixelFormat, contextAttributes)

    GL11.glViewport(0, 0, width, height)
    GL11.glClearColor(0.3f, 0.3f, 0.3f, 1f)

    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glDepthFunc(GL11.GL_LEQUAL)
    GL11.glFrontFace(GL11.GL_CW)

//    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
  }

  def setupShaderProgram(): ShaderProgram = {
    val shaderProgram = new ShaderProgram

    shaderProgram.attachShader(new Shader(ShaderType.Vertex, "shaders/basic.vert"))
    shaderProgram.attachShader(new Shader(ShaderType.Fragment, "shaders/basic.frag"))

    shaderProgram.bindAttribute(0, "position")
    shaderProgram.bindAttribute(1, "in_color")

    shaderProgram.build()

    shaderProgram
  }

  def setupCamera(): Camera = {
    val camera = new Camera(800f/600f, 45f, 0.1f, 100f)
    camera.setPosition(5f, 2f, -3f)
    camera.lookAt(0f, 0f, 0f)
    camera
  }

  def main(args: Array[String]): Unit = {

    setupOpenGL()
    val shaderProgram = setupShaderProgram()
    val camera = setupCamera()

    val projMatLocation = shaderProgram.getUniformLocation("projMatrix")
    val viewMatLocation = shaderProgram.getUniformLocation("viewMatrix")

    val projBuffer = BufferUtils.createFloatBuffer(16)
    val viewBuffer = BufferUtils.createFloatBuffer(16)

    val viewMatrix = new Matrix4f()

    viewMatrix.setIdentity()
//    viewMatrix.m32 = -20f
//    viewMatrix.translate(new Vector3f(0, 0, -10))

    camera.projectionMatrix.store(projBuffer); projBuffer.flip()
    camera.viewMatrix.store(viewBuffer); viewBuffer.flip()

    val quad = new Quad(2f)
    val box = new Box(1f)

    shaderProgram.use()

    while(!Display.isCloseRequested) {

      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

      GL20.glUniformMatrix4(projMatLocation, false, projBuffer)
      GL20.glUniformMatrix4(viewMatLocation, false, viewBuffer)

      box.render()
      quad.render()

      Display.update()
    }
  }

}
