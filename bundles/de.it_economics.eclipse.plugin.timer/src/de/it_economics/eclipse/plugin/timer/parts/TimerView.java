package de.it_economics.eclipse.plugin.timer.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import de.it_economics.eclipse.plugin.timer.ui.Clock;
import de.it_economics.eclipse.plugin.timer.ui.ConfigLine;
import de.it_economics.eclipse.plugin.timer.ui.ControlLine;
import de.it_economics.eclipse.plugin.timer.ui.TimeOverMessage;

public class TimerView {
	@Inject
	private IEclipseContext context;

	private ControlLine controlLine;
	// Needs to be stored, otherwise they don't get initialized on application
	// restart
	@SuppressWarnings("unused")
	private Clock clock;
	@SuppressWarnings("unused")
	private ConfigLine configline;
	@SuppressWarnings("unused")
	private TimeOverMessage timeOverMessage;

	@PostConstruct
	public void createPartControl(Composite parent) {
		setLayout(parent);
		clock = ContextInjectionFactory.make(Clock.class, context);
		controlLine = ContextInjectionFactory.make(ControlLine.class, context);
		configline = ContextInjectionFactory.make(ConfigLine.class, context);
		timeOverMessage = ContextInjectionFactory.make(TimeOverMessage.class, context);
	}

	private void setLayout(Composite parent) {
		RowLayout rowLayout = new RowLayout();
		rowLayout.center = true;
		rowLayout.type = SWT.VERTICAL;
		rowLayout.pack = true;
		rowLayout.justify = true;
		parent.setLayout(rowLayout);
	}

	@Focus
	public void setFocus() {
		controlLine.setFocus();

	}

}
