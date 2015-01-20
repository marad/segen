package seng.core.props

trait Rotatable {
  def rotation: Float
  def rotation(angle:Float):Unit
  def rotate(angle: Float):Unit
}
