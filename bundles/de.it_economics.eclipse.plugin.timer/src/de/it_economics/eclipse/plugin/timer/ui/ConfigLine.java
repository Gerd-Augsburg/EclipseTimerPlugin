
package de.it_economics.eclipse.plugin.timer.ui;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.it_economics.eclipse.plugin.timer.utils.Duration;

public class ConfigLine {
	public final static String EVENT_REGISTER_DURATION_PROVIDER = "de/it_economics/eclipse/plugin/timer/config/registerDurationProvider";
	private Text hours;
	private Text minutes;
	private Text seconds;
	@Inject
	private IEventBroker eventBroker;

	@Inject
	public ConfigLine() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		createInputFields(container);
		eventBroker.post(EVENT_REGISTER_DURATION_PROVIDER, createDurationProvider());
	}

	private Supplier<Duration> createDurationProvider() {
		return () -> {
			Duration providedDuration = new Duration();
			providedDuration.addHours(textToInt(hours));
			providedDuration.addMinutes(textToInt(minutes));
			providedDuration.addSeconds(textToInt(seconds));
			return providedDuration;
		};
	}

	private void createInputFields(Composite parent) {
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		parent.setLayout(fillLayout);
		hours = createInputEntry(parent, "Stunden");
		minutes = createInputEntry(parent, "Minuten");
		seconds = createInputEntry(parent, "Sekunden");
	}
	
	private Text createInputEntry(Composite parent, String message ) {
		Text text = new Text(parent, SWT.BORDER);
		text.setMessage(message);
		return text;
	}

	private int textToInt(Text text) {
		String textValue = text.getText();
		int returnValue;
		try {
			returnValue = Integer.valueOf(textValue);
		} catch (NumberFormatException e) {
			returnValue = 0;
		}
		return returnValue;
	}
}