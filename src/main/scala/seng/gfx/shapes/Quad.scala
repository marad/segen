package seng.gfx.shapes

import seng.gfx.{Mesh, Vertex}

class Quad(scale: Float = 1f) extends Mesh(
  Quad.data.map { v => v.copy( x = v.x*scale, y = v.y*scale, z = v.z*scale ) },
  Quad.indices)

object Quad {

  val data = Array(
    new Vertex( 0.5f,  0.5f, 0f,   0f, 0f, -1f,   1f, 1f, 0f, 1f,   1f, 0f),
    new Vertex( 0.5f, -0.5f, 0f,   0f, 0f, -1f,   1f, 1f, 0f, 1f,   1f, 1f),
    new Vertex(-0.5f, -0.5f, 0f,   0f, 0f, -1f,   1f, 1f, 0f, 1f,   0f, 1f),
    new Vertex(-0.5f,  0.5f, 0f,   0f, 0f, -1f,   1f, 1f, 0f, 1f,   0f, 0f)
  )

  val indices: Array[Int] = Array( 0, 3, 1, 2, 1, 3 )
}
