 
package de.it_economics.eclipse.plugin.timer.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;

public class StartButton extends AbstractControlButton {
	@Inject private IEventBroker eventbroker;
	@Inject
	public StartButton() {
		super("Start");
	}
	@Override
	protected void action() {
		eventbroker.post(Timer.EVENT_START_TIMER, NotifcationObject.get());
	}
	@Focus
	public void setFocus() {
		this.button.setFocus();
	}
}