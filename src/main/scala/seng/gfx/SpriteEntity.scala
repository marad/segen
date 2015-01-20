package seng.gfx

import com.badlogic.gdx.graphics.g2d.Sprite
import seng.core.SimpleEntity
import seng.core.props.{RenderInfo, Renderable}

class SpriteEntity(val sprite: Sprite) extends SimpleEntity with Renderable {
  def render(renderInfo: RenderInfo): Unit = renderInfo match {
    case info: SpriteBatchRenderInfo => sprite.draw(info.spriteBatch)
    case _ =>
  }

  override def rotate(angle: Float): Unit = sprite.rotate(angle)
  override def rotation(angle: Float): Unit = sprite.setRotation(angle)
  override def rotation: Float = sprite.getRotation

  override def sx: Float = sprite.getScaleX
  override def sx(x: Float): Unit = sprite.setScale(x, sy)
  override def sy: Float = sprite.getScaleY
  override def sy(y: Float): Unit = sprite.setScale(sx, y)
  override def scale(x: Float, y: Float): Unit = sprite.setScale(x, y)

  override def x: Float = sprite.getX
  override def x(x: Float): Unit = sprite.setX(x)
  override def y: Float = sprite.getY
  override def y(y: Float): Unit = sprite.setY(y)
  override def move(x: Float, y: Float): Unit = sprite.translate(x, y)
  override def position(x: Float, y: Float): Unit = sprite.setPosition(x, y)
}
