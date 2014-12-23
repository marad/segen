package seng.gfx

case class Quad(scale: Float = 1f) extends Mesh(Quad.scaledVertices(scale), Quad.indices)

object Quad {
  private def vertices: Array[Float] = Array(
    0.5f,  0.5f,  0.0f,
    0.5f, -0.5f,  0.0f,
    -0.5f, -0.5f,  0.0f,
    -0.5f,  0.5f,  0.0f
  )

  private def indices: Array[Int] = Array( 0, 1, 3, 2, 3, 1 )

  def scaledVertices(scale: Float) = vertices.map { _ * scale }
}
