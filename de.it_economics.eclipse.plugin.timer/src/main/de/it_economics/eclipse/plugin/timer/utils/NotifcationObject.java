package de.it_economics.eclipse.plugin.timer.utils;

public class NotifcationObject {
	private static NotifcationObject self;
	private NotifcationObject() {
	}
	public static NotifcationObject get() {
		if (self == null) {
			self = new NotifcationObject();
		}
		return self;
	}
}
