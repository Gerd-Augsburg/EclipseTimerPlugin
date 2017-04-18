package de.it_economics.eclipse.plugin.timer;

import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;

@Creatable
@Singleton
public class Timer {
	private final static String BASE = "de/it_economics/eclipse/plugin/timer/";
	public final static String EVENT_DURATION_UPDATE = BASE + "durationUpdate";
	public final static String EVENT_TIME_IS_OVER = BASE + "timeIsOver";
	public final static String EVENT_START_TIMER = BASE + "startTimer";
	public final static String EVENT_CANCEL_TIMER = BASE + "cancelTimer";
	public final static String EVENT_DURATION_SET = BASE + "durationSet";

	@Inject
	private IEventBroker eventBroker;
	private Duration durationLeft;
	private Duration lastRun;

	private final static long INTERLVALL = 1000;
	private boolean isTimerRunning = false;
	private java.util.Timer timer = new java.util.Timer(true);
	private TimerTask lastTask;

	private TimerTask createTask() {
		return new TimerTask() {
			@Override
			public void run() {
				durationLeft.addMilliseconds(-(INTERLVALL));
				eventBroker.post(EVENT_DURATION_UPDATE, durationLeft.clone());
				if (!durationLeft.isRealPositiv()) {
					eventBroker.send(EVENT_TIME_IS_OVER, NotifcationObject.get());
				}
			};
		};
	}

	@Inject
	public Timer() {
	}

	@PostConstruct
	private void postConstruct() {
		durationLeft = new Duration(0);
		lastRun = new Duration(0);
		lastTask = createTask();
	}

	@Inject
	@Optional
	private void startTimer(@UIEventTopic(EVENT_START_TIMER) NotifcationObject no) {
		if (!isTimerRunning) {
			timer.scheduleAtFixedRate(lastTask, INTERLVALL, INTERLVALL);
			isTimerRunning = true;
		}
	}

	@Inject
	@Optional
	private void stopTimer(@UIEventTopic(EVENT_CANCEL_TIMER) NotifcationObject no) {
		lastTask.cancel();
		isTimerRunning = false;
		lastTask = createTask();
	}

	@Inject
	@Optional
	private void setTimer(@UIEventTopic(EVENT_DURATION_SET) Duration duration) {
		eventBroker.send(EVENT_CANCEL_TIMER, NotifcationObject.get());
		durationLeft = duration.clone();
		lastRun = duration.clone();
		eventBroker.post(EVENT_DURATION_UPDATE, durationLeft.clone());
	}

	@Inject
	@Optional
	private void timeOver(@UIEventTopic(EVENT_TIME_IS_OVER) NotifcationObject no) {
		eventBroker.send(EVENT_DURATION_SET, lastRun);
	}
}