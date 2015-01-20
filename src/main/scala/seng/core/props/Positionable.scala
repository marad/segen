package seng.core.props

trait Positionable {
  def x: Float
  def x(x: Float):Unit
  def y: Float
  def y(y: Float):Unit
  def position(x:Float, y:Float): Unit
  def move(x:Float, y:Float): Unit
}
