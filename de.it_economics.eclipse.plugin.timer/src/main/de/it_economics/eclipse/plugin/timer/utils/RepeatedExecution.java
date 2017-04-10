package de.it_economics.eclipse.plugin.timer.utils;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.widgets.Display;

public class RepeatedExecution {
	private Timer timer;
	private long interval;
	private RepeatableTask task;
	public RepeatedExecution(long interval_ms) {
		if (interval_ms <= 0) {
			throw new IllegalArgumentException("frequency_ms must be greater than 0");
		}
		interval = interval_ms;
		timer = new Timer();
		task = new RepeatableTask() {
			@Override
			public void run() {
				// Default do nothing
			}
		};
	}
	public void setTask(RepeatableTask task) {
		this.task = task;
	}
	public void go() {
		timer.scheduleAtFixedRate(task, 0, interval);
	}
	
	public boolean cancelRepetition() {
		return task.cancel();
	}
	
}
