package de.it_economics.eclipse.plugin.timer.utils;

import java.util.Optional;
import java.util.TimerTask;

public class InSecondsExecution implements RepeatedExecution {
	private java.util.Timer timer = new java.util.Timer();
	private final static long INTERLVALL = 1000;
	private Optional<TimerTask> lastTask = Optional.empty();
	@Override
	public void run(TimerTask task) {
		cancel();
		lastTask = Optional.ofNullable(task);
		lastTask.ifPresent(existingTask -> timer.schedule(existingTask, INTERLVALL, INTERLVALL));
	}
	@Override
	public boolean cancel() {
		final boolean value = lastTask.map(TimerTask::cancel).orElse(false);
		lastTask = Optional.empty();
		return value;
	}
	@Override
	public boolean isRunning() {
		return lastTask.isPresent();
	}
	@Override
	public long getIntervall() {
		return INTERLVALL;
	}
}
