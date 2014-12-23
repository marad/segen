package seng.gfx

case class Box(scale: Float = 1) extends Mesh(Box.scaledVertices(scale), Box.indices)

object Box {
  private def vertices: Array[Float] = Array(
     0.5f,  0.5f,  0.5f,
     0.5f, -0.5f,  0.5f,
    -0.5f, -0.5f,  0.5f,
    -0.5f,  0.5f,  0.5f,

     0.5f,  0.5f, -0.5f,
     0.5f, -0.5f, -0.5f,
    -0.5f, -0.5f, -0.5f,
    -0.5f,  0.5f, -0.5f
  )

  private def indices: Array[Int] = Array(
    0, 1, 3, 2, 3, 1, // front wall
    4, 5, 0, 1, 0, 5, // right wall
    3, 2, 7, 6, 7, 2, // left wall
    7, 6, 4, 5, 4, 6, // back wall
    0, 3, 4, 7, 4, 3, // top wall
    1, 5, 2, 6, 2, 5  // bottom wall
  )

  def scaledVertices(scale: Float) = vertices.map { _ * scale }
}
