package de.it_economics.eclipse.plugin.timer;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.event.EventHandler;

import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;
import de.it_economics.eclipse.plugin.timer.utils.TestExecutor;

public class TimerTest {
	
	private class EventContainer {
		private String topic;
		private Object data;
		public EventContainer(String topic, Object data) {
			this.topic = topic;
			this.data = data;
		}
		public <T> T getData() {
			T back = null;
			try {
				back = (T)data;
			} catch (ClassCastException e) {
				back = null;
			}
			return back;
		}
		public String getTopic() {
			return topic;
		}
		
		@Override
		public boolean equals(Object other) {
			if (other instanceof EventContainer) {
				EventContainer castedOther = (EventContainer)other;
				return this.topic.equals(castedOther.topic) && this.data.equals(castedOther.data);
			} else {
				return false;
			}
		}
		@Override
		public String toString() {
			return topic + " " + data.toString();
		}
	}
	
	private List<EventContainer> events = new LinkedList<EventContainer>();
	
	IEventBroker broker = new IEventBroker() {
		
		@Override
		public boolean send(String topic, Object data) {
			return events.add(new EventContainer(topic, data));
		}
		
		@Override
		public boolean post(String topic, Object data) {
			return events.add(new EventContainer(topic, data));
		}

		@Override
		public boolean subscribe(String topic, EventHandler eventHandler) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean subscribe(String topic, String filter, EventHandler eventHandler, boolean headless) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean unsubscribe(EventHandler eventHandler) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	Timer timer;
	
	@Before
	public void setUp() throws Exception {
		timer = new Timer();
	}

	@After
	public void tearDown() throws Exception {
		timer = null;
	}

	@Test
	public void test() {
		//fail("Expected Failure");
		assertTrue(true);
	}
	
	@Test
	public void startTimerTest() {
		timer.postConstruct(broker);
		TestExecutor executor = new TestExecutor();
		timer.setExecutor(executor);
		timer.startTimer(NotifcationObject.get());
		assertTrue(executor.isRunning());
	}
	
	@Test
	public void stopTimerTest() {
		timer.postConstruct(broker);
		TestExecutor executor = new TestExecutor();
		timer.setExecutor(executor);
		executor.run(new TimerTask() {
			@Override
			public void run() {
			}
		});
		
		timer.stopTimer(NotifcationObject.get());
		
		assertFalse(executor.isRunning());
	}
	
	@Test
	public void setTimerTest() {
		List<EventContainer> expectedEvents = new LinkedList<EventContainer>();
		Duration duration = new Duration(2000);
		expectedEvents.add(new EventContainer(Timer.EVENT_CANCEL_TIMER, NotifcationObject.get()));
		expectedEvents.add(new EventContainer(Timer.EVENT_DURATION_UPDATE, duration));
		timer.postConstruct(broker);
		
		timer.setTimer(duration);
		assertEquals(expectedEvents, events);
	}
	
	@Test
	public void timeOverTest() {
		timer.postConstruct(broker);
		timer.setTimer(new Duration(10000));
		events.clear();
		timer.timeOver(NotifcationObject.get());
		assertEquals(1, events.size());
		EventContainer event = events.get(0);
		assertEquals(Timer.EVENT_DURATION_SET, event.getTopic());
		assertEquals(10000, event.<Duration>getData().getMilliseconds());
	}
	
	@Test
	public void defaultTaskTest() {
		timer.postConstruct(broker);
		timer.setTimer(new Duration(10000));
		TestExecutor executor = new TestExecutor();
		timer.setExecutor(executor);
		events.clear();
		timer.startTimer(NotifcationObject.get());
		for (int i = 0; i < 10; i++)
			executor.execute();
		Iterator<EventContainer> iterator = events.iterator();
		for (int i = 0; i < 10; i++) {
			assertTrue(iterator.hasNext());
			EventContainer container = iterator.next();
			assertEquals(Timer.EVENT_DURATION_UPDATE, container.getTopic());
			assertEquals(10000 - (i + 1) * 1000, container.<Duration>getData().getDurationInMs());
		}
		assertTrue(iterator.hasNext());
		EventContainer container = iterator.next();
		assertEquals(Timer.EVENT_TIME_IS_OVER, container.getTopic());
		assertEquals(NotifcationObject.get(), container.<NotifcationObject>getData());
	}
}
