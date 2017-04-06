package de.it_economics.eclipse.plugin.timer.utils;

public class Duration implements MsDuration {
	private long msduration;
	public Duration() {
		msduration = 0;
	}
	public Duration(long milliseconds) {
		msduration = milliseconds;
	}
	@Override
	public long getDurationInMs() {
		return getMilliseconds();
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
	public int[] getOrderedTimeHMSMs() {
		int[] hms = {(int)getHours(), (int)(getMinutes() % 60), (int)(getSeconds() % 60), (int)(getMilliseconds() % 1000)};
		return hms;
	}
}
