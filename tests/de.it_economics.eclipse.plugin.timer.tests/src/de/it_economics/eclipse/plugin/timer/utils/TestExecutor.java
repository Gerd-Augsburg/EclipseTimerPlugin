package de.it_economics.eclipse.plugin.timer.utils;

import java.util.Optional;
import java.util.TimerTask;

public class TestExecutor implements RepeatedExecution {
	Optional<TimerTask> task = Optional.empty();
	boolean isTaskRunning = false;

	@Override
	public void run(TimerTask task) {
		this.task = Optional.ofNullable(task);
		this.task.ifPresent(x -> isTaskRunning = true);
	}

	@Override
	public boolean cancel() {
		if (task.isPresent()) {
			task = Optional.empty();
			isTaskRunning = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean isRunning() {
		return isTaskRunning;
	}

	@Override
	public long getIntervall() {
		return 1000;
	}

	public void execute() {
		task.ifPresent(TimerTask::run);
	}
}
