package de.it_economics.eclipse.plugin.timer.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.ui.Clock;
import de.it_economics.eclipse.plugin.timer.ui.ConfigLine;
import de.it_economics.eclipse.plugin.timer.ui.ControlLine;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;

public class TimerView {
	@Inject
	private IEclipseContext context;

	private Clock clock;
	private ControlLine controlLine;
	private ConfigLine configLine;

	@PostConstruct
	public void createPartControl(Composite parent) {
		setLayout(parent);
		clock = ContextInjectionFactory.make(Clock.class, context);
		controlLine = ContextInjectionFactory.make(ControlLine.class, context);
		configLine = ContextInjectionFactory.make(ConfigLine.class, context);
	}
	
	private IEclipseContext createChildCompositContext(Composite parent) {
		IEclipseContext child = context.createChild();
		child.set(Composite.class, new Composite(parent, SWT.FLAT));
		return child;
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

	@Inject
	@Optional
	private void timeIsOverNotification(@UIEventTopic(Timer.EVENT_TIME_IS_OVER) NotifcationObject no) {
		Display display = Display.getDefault();
		display.asyncExec(() -> {
			MessageDialog.open(MessageDialog.INFORMATION, display.getActiveShell(), "Time is over", "Next one please!",
					SWT.NONE);
		});
	}
}
