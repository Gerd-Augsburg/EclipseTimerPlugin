package de.it_economics.eclipse.plugin.timer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DurationTest {

	@Test
	public void EmptyInitialisationShouldBeZero() {
		Duration duration = new Duration();
		assertEquals(0, duration.getMilliseconds());
		assertEquals(0, duration.getDurationInMs());
		assertEquals(0, duration.getHours());
	}
	@Test
	public void ValuedInitialisaitionShouldContainTheValue() {
		Duration duration = new Duration(1234567890);
		assertEquals(1234567890, duration.getMilliseconds());
	}
	@Test
	public void TimeConversionTestMsToOthers() {
		Duration duration = new Duration(5025678);
		assertEquals(5025678, duration.getMilliseconds());
		assertEquals(5025, duration.getSeconds());
		assertEquals(83, duration.getMinutes());
		assertEquals(1, duration.getHours());
		int[] expectation = {1,23,45,678};
		int[] actual = duration.getOrderedTimeHMSMs();
		for (int i = 0; i < expectation.length; ++i) {
			assertEquals(expectation[i], actual[i]);
		}
	}

}
