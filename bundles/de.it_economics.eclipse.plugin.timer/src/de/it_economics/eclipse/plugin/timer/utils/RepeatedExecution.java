package de.it_economics.eclipse.plugin.timer.utils;

import java.util.TimerTask;

public interface RepeatedExecution {
	public void run(TimerTask task);
	public boolean cancel();
	public boolean isRunning();
	public long getIntervall();
}
