 
package de.it_economics.eclipse.plugin.timer.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;

public class StopButton extends AbstractControlButton {
	@Inject private IEventBroker eventbroker;
	@Inject
	public StopButton() {
		super("Stop");
	}
	@Override
	protected void action() {
		eventbroker.post(Timer.EVENT_CANCEL_TIMER, NotifcationObject.get());
	}
}