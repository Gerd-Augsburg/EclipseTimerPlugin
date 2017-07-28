package de.it_economics.eclipse.plugin.timer.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public class ControlLine {
	@Inject private IEclipseContext context;
	private StartButton startButton;
	
	@Inject
	public ControlLine() {
	}
	
	public ControlLine(IEclipseContext context) {
		this();
		ContextInjectionFactory.inject(this, context);
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		Composite controlLine = new Composite(parent, SWT.NONE);
		controlLine.setLayout(createRowLayout());
		fillComposite(controlLine);
	}
	
	private RowLayout createRowLayout() {
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.HORIZONTAL;
		rowLayout.wrap = true;
		rowLayout.pack = false;
		rowLayout.spacing = 10;
		return rowLayout;
	}
	
	private void fillComposite(Composite parent) {
		IEclipseContext childContext = context.createChild();
		childContext.set(Composite.class, parent);
		startButton = ContextInjectionFactory.make(StartButton.class, childContext);
		ContextInjectionFactory.make(StopButton.class, childContext);
		ContextInjectionFactory.make(SetButton.class, childContext);
	}

	@Focus
	public void setFocus() {
		startButton.setFocus();
	}

}
