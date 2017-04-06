package de.it_economics.eclipse.plugin.timer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DurationFormatterTest {

	@Test
	public void clocklickeOutputTest() {
		Duration duration = new Duration(5025678);
		DurationFormatter formatter = new DurationFormatter(duration);
		assertEquals("01:23:45.678", formatter.getClocklike());
	}
	@Test
	public void clocklikeZeroPaddingTest() {
		Duration duration = new Duration(3661001);
		DurationFormatter formatter = new DurationFormatter(duration);
		assertEquals("01:01:01.001", formatter.getClocklike());
	}

}
