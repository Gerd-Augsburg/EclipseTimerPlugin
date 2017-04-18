 
package de.it_economics.eclipse.plugin.timer.ui;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.DurationFormatter;

public class Clock {
	private Label clock;
	
	@Inject
	public Clock() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		clock = new Label(parent, SWT.CENTER);
		clock.setText("00:00:00");
		clock.setFont(new Font(parent.getDisplay(), "Arial", 30, SWT.BOLD));
	}
	
	@Inject
	@Optional
	private void updateOnDurationChange(
			@UIEventTopic(Timer.EVENT_DURATION_UPDATE)
			Duration updatedDuraiton) {
		clock.setText(DurationFormatter.getAsHMS(updatedDuraiton));
	}
	
	
	
	
}