
package de.it_economics.eclipse.plugin.timer.ui;

import java.util.function.Supplier;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.Duration;

public class SetButton extends AbstractControlButton {
	private Supplier<Duration> durationProvider;
	@Inject
	private IEventBroker eventbroker;

	@Inject
	public SetButton() {
		super("Set Time");
	}

	@Override
	protected void action() {
		eventbroker.post(Timer.EVENT_DURATION_SET, durationProvider.get());
	}

	@Inject
	@Optional
	private void registerDurationProvider(
			@UIEventTopic(ConfigLine.EVENT_REGISTER_DURATION_PROVIDER) Supplier<Duration> durationProvider) {
		this.durationProvider = durationProvider;
	}

}