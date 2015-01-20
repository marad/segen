package seng.core.props

trait Scalable {
  def sx: Float
  def sx(x:Float):Unit
  def sy: Float
  def sy(y:Float):Unit
  def scale(x:Float, y:Float):Unit
}
