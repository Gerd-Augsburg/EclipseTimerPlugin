 
package de.it_economics.eclipse.plugin.timer.ui;

import javax.inject.Inject;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.Duration;

public class ConfigLine {
	@Inject private IEventBroker eventBroker;
	
	@Inject
	public ConfigLine() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		Composite container = new Composite(parent, SWT.CENTER);
		eventBroker.post(
				Timer.EVENT_REGISTER_DURATION_PROVIDER,
				timeInput(container));
	}
	
	private Supplier<Duration> timeInput(Composite parent) {
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		parent.setLayout(fillLayout);
		Text hours = new Text(parent, SWT.BORDER);
		hours.setMessage("Stunden");
		Text minutes = new Text(parent, SWT.BORDER);
		minutes.setMessage("Minuten");
		Text seconds = new Text(parent, SWT.BORDER);
		seconds.setMessage("Sekunden");
		return () -> {
			Function<Text, Integer> textToInt = (Text text) -> {
				String textValue = text.getText();
				int returnValue;
				try {
					returnValue = Integer.valueOf(textValue);
				} catch (NumberFormatException e) {
					returnValue = 0;
				}
				return returnValue;
			};
			Duration providedDuration = new Duration();
			providedDuration.addHours(textToInt.apply(hours));
			providedDuration.addMinutes(textToInt.apply(minutes));
			providedDuration.addSeconds(textToInt.apply(seconds));
			return providedDuration;
		};
	}
	
	
}