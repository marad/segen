package seng.core

import org.mockito.Matchers._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import seng.core.Entity.Id

import seng.event.{Event, EntityEvent, GlobalEvent}
import seng.core.props.{RenderInfo, Renderable}

import scala.collection.mutable

class SceneTest extends Specification with Mockito {

  "Scene update" should {

    "discard events for not existing entities" in {
      val e = mock[EntityEvent]
      e.toEntity returns 0

      val events = List[Event](e)

      (new Scene).update(events) should not throwA()
    }

    "work properly when there is no events" in {
      val events = List[Event]()
      val newEvents = (new Scene).update(events)

      (events should be).empty
      (newEvents should be).empty
    }

    "execute entity events" in {
      case class TestEvent(toEntity: Entity.Id) extends EntityEvent {
        def perform(e:Entity) = List.empty
      }

      val entity = mock[Entity]
      val event = spy(new TestEvent(0))
      val scene = new Scene
      scene.entities.put(0, entity)

      scene.update(List(event))

      there was one(event).perform(entity)
    }

    "execute global events" in {
      case class TestEvent() extends GlobalEvent {
        def perform(entities: Entity.Map) = List.empty
      }

      val event = spy(new TestEvent)
      val scene = new Scene

      scene.update(List(event))

      there was one(event).perform(scene.entities)
    }

    "throw exception on unsupported event" in {
      case class TestEvent() extends Event
      (new Scene).update(List(new TestEvent)) should throwA()
    }
  }


  "Scene render" should {

    "work for empty entity list" in {
      val scene = new Scene
      scene.render(null) should not throwA()
    }

    "render visible entities and handle non visible entities" in {
      class TestEntity extends Entity with Renderable {
        def render() = {}

        override val id: Id = ???
        override val properties: mutable.AnyRefMap[String, String] = ???
        override def render(renderInfo: RenderInfo): Unit = ???
        override def sy(y: Float): Unit = ???
        override def sy: Float = ???
        override def sx(x: Float): Unit = ???
        override def sx: Float = ???
        override def scale(x: Float, y: Float): Unit = ???
        override def move(x: Float, y: Float): Unit = ???
        override def position(x: Float, y: Float): Unit = ???
        override def y(y: Float): Unit = ???
        override def y: Float = ???
        override def x(x: Float): Unit = ???
        override def x: Float = ???
        override def rotate(angle: Float): Unit = ???
        override def rotation(angle: Float): Unit = ???
        override def rotation: Float = ???
      }

      val visibleEntity = spy(new TestEntity)
      val notVisibleEntity = spy(new SimpleEntity)

      val scene = new Scene
      scene.entities.put(0, visibleEntity)
      scene.entities.put(1, notVisibleEntity)

      scene.render(null)

      there was one(visibleEntity).render()
    }
  }
}
