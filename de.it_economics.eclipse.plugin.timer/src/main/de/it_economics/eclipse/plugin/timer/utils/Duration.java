package de.it_economics.eclipse.plugin.timer.utils;

public class Duration implements MsDuration {
	private long msduration;
	
	private void changeNegativesToZero() {
		if (msduration < 0)
			msduration = 0;
	}
	
	public Duration() {
		msduration = 0;
	}
	public Duration(long milliseconds) {
		msduration = milliseconds;
		changeNegativesToZero();
	}
	@Override
	public long getDurationInMs() {
		return getMilliseconds();
	}
	public boolean isRealPositiv() {
		return msduration > 0;
	}
	public long getMilliseconds() {
		return msduration;
	}
	public long getSeconds() {
		return getMilliseconds() / 1000;
	}
	public long getMinutes() {
		return getSeconds() / 60;
	}
	public long getHours() {
		return getMinutes() / 60;
	}
	public void addMilliseconds(long milliseconds) {
		msduration += milliseconds;
		changeNegativesToZero();
	}
	public void addSeconds(long seconds) {
		addMilliseconds(seconds * 1000); 
	}
	public void addMinutes(long minutes) {
		addSeconds(minutes * 60);
	}
	public void addHours(long hours) {
		addMinutes(hours * 60);
	}
	
	
	public int[] getOrderedTimeHMSMs() {
		int[] hms = {(int)getHours(), (int)(getMinutes() % 60), (int)(getSeconds() % 60), (int)(getMilliseconds() % 1000)};
		return hms;
	}
}
