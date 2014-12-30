package seng.gfx

class Box(scale: Float = 1) extends Mesh(
  Box.data.map { v => v.copy( x = v.x*scale, y = v.y*scale, z = v.z*scale ) },
  Box.indices)

object Box {
  val data = Array(
    new Vertex( 0.5f,  0.5f, -0.5f,   1f, 1f, -1f,   1f, 0f, 0f, 1f,   1f, 0f),
    new Vertex( 0.5f, -0.5f, -0.5f,   1f, -1f, -1f,   0f, 1f, 0f, 1f,   1f, 1f),
    new Vertex(-0.5f, -0.5f, -0.5f,   -1f, -1f, -1f,   0f, 0f, 1f, 1f,   0f, 1f),
    new Vertex(-0.5f,  0.5f, -0.5f,   -1f, 1f, -1f,   1f, 1f, 1f, 1f,   0f, 0f),

    new Vertex( 0.5f,  0.5f,  0.5f,   1f, 1f, 1f,   1f, 0f, 1f, 1f,   1f, 0f),
    new Vertex( 0.5f, -0.5f,  0.5f,   1f, -1f, 1f,   1f, 1f, 0f, 1f,   1f, 1f),
    new Vertex(-0.5f, -0.5f,  0.5f,   -1f, -1f, 1f,   0f, 1f, 1f, 1f,   0f, 1f),
    new Vertex(-0.5f,  0.5f,  0.5f,   -1f, 1f, 1f,   1f, 1f, 1f, 1f,   0f, 0f)
  )

  val indices: Array[Int] = Array(
    0, 3, 1,  2, 1, 3, // front wall
    4, 0, 5,  1, 5, 0, // right wall
    3, 7, 2,  6, 2, 7, // left wall
    7, 4, 6,  5, 6, 4, // back wall
    0, 4, 3,  7, 3, 4, // top wall
    1, 2, 5,  6, 5, 2 // bottom wall
  )
}
