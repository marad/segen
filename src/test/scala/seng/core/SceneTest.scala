package seng.core

import org.mockito.Matchers._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

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
    // TODO: implement tests
  }
}
