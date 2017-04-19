 
package de.it_economics.eclipse.plugin.timer.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractControlButton {
	private final String label;
	
	protected Button button;

	public AbstractControlButton(String label) {
		this.label = label;
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				action();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				action();
			}
		});
	}
	protected abstract void action();
	
	@Focus
	public void setFocus() {
		this.button.setFocus();
	}
	
}