package seng.gfx

case class Vertex(
                   x:Float, y:Float, z:Float,
                   nx:Float, ny:Float, nz:Float,
                   r:Float, g:Float, b:Float, a:Float,
                   s:Float, t:Float) {

  def elements: Array[Float] = Array(
    x, y, z,
    r, g, b, a,
    s, t,
    nx, ny, nz
  )
}

object Vertex {
  val elementBytes = 4

  val positionElementCount = 3
  val colorElementCount = 4
  val textureElementCount = 2
  val normalElementCount = 3

  val positionBytesCount = positionElementCount * elementBytes
  val colorByteCount = colorElementCount * elementBytes
  val textureByteCount = textureElementCount * elementBytes
  val normalByteCount = normalElementCount * elementBytes

  val positionByteOffset = 0
  val colorByteOffset = positionByteOffset + positionBytesCount
  val textureByteOffset = colorByteOffset + colorByteCount
  val normalByteOffset = textureByteOffset + textureByteCount

  val stride = positionBytesCount + colorByteCount + textureByteCount + normalByteCount
}
