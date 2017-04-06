package de.it_economics.eclipse.plugin.timer;

import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.RepeatableTask;

public class Timer {
	private Duration duration;
	private RepeatableTask finalAction;
	private static Timer instance;
	public static Timer getInstance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}
	private Timer() {
		
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public void setFinalAction(RepeatableTask task) {
		finalAction = task;
	}
	
}
