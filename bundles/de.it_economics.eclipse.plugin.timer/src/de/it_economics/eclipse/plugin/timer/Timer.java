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
import de.it_economics.eclipse.plugin.timer.utils.InSecondsExecution;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;
import de.it_economics.eclipse.plugin.timer.utils.RepeatedExecution;
import de.it_economics.eclipse.plugin.timer.utils.TimerTaskFactory;

@Creatable
@Singleton
public class Timer {
	private final static String BASE = "de/it_economics/eclipse/plugin/timer/";
	public final static String EVENT_DURATION_UPDATE = BASE + "durationUpdate";
	public final static String EVENT_TIME_IS_OVER = BASE + "timeIsOver";
	public final static String EVENT_START_TIMER = BASE + "startTimer";
	public final static String EVENT_CANCEL_TIMER = BASE + "cancelTimer";
	public final static String EVENT_DURATION_SET = BASE + "durationSet";

	private IEventBroker eventBroker;
	private Duration durationLeft;
	private Duration lastRun;
	private RepeatedExecution executor = new InSecondsExecution();
	
	private TimerTaskFactory taskFactory = new TimerTaskFactory() {
		@Override
		public TimerTask make() {
			return new TimerTask() {
				@Override
				public void run() {
					durationLeft.addMilliseconds(-(executor.getIntervall()));
					eventBroker.post(EVENT_DURATION_UPDATE, durationLeft.clone());
					if (!durationLeft.isRealPositiv()) {
						eventBroker.send(EVENT_TIME_IS_OVER, NotifcationObject.get());
					}
				};
			};
		}
	};

	@Inject
	public Timer() {
	}

	@PostConstruct
	public void postConstruct(IEventBroker broker) {
		this.eventBroker = broker;
		
		durationLeft = new Duration(0);
		lastRun = new Duration(0);
	}
	
	public void setExecutor(RepeatedExecution exe) {
		executor = exe;
	}
	
	public void setTaskFactory(TimerTaskFactory taskFactory) {
		this.taskFactory = taskFactory;
	}

	@Inject
	@Optional
	public void startTimer(@UIEventTopic(EVENT_START_TIMER) NotifcationObject no) {
		if (!executor.isRunning()) {
			executor.run(taskFactory.make());
		}
	}

	@Inject
	@Optional
	public void stopTimer(@UIEventTopic(EVENT_CANCEL_TIMER) NotifcationObject no) {
		executor.cancel();
	}

	@Inject
	@Optional
	public void setTimer(@UIEventTopic(EVENT_DURATION_SET) Duration duration) {
		eventBroker.send(EVENT_CANCEL_TIMER, NotifcationObject.get());
		durationLeft = duration.clone();
		lastRun = duration.clone();
		eventBroker.post(EVENT_DURATION_UPDATE, durationLeft.clone());
	}

	@Inject
	@Optional
	public void timeOver(@UIEventTopic(EVENT_TIME_IS_OVER) NotifcationObject no) {
		eventBroker.send(EVENT_DURATION_SET, lastRun);
	}
}