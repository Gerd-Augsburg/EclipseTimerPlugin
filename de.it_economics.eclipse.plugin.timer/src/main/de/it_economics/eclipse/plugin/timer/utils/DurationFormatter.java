package de.it_economics.eclipse.plugin.timer.utils;

public class DurationFormatter {
	private Duration duration;
	public DurationFormatter(Duration duration) {
		this.duration = duration;
	}
	public String getClocklike() {
		int[] parts = duration.getOrderedTimeHMSMs();
		return String.format("%1$02d:%2$02d:%3$02d.%4$03d",
				parts[0],
				parts[1],
				parts[2],
				parts[3]);
	}
}
