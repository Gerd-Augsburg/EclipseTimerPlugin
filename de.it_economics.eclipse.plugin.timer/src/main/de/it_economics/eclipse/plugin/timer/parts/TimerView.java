package de.it_economics.eclipse.plugin.timer.parts;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.RepeatableTask;
import de.it_economics.eclipse.plugin.timer.utils.RepeatedExecution;

public class TimerView {
	private Label myLabelInView;
	private Label clockLine;
	private Supplier<Duration> getDurationProvider;
	private Timer timer;
	
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		RowLayout rowLayout = new RowLayout();
		rowLayout.center = true;
		rowLayout.type = SWT.VERTICAL;
		parent.setLayout(rowLayout);
		clockLine = new Label(parent, SWT.FLAT);
		clockLine.setText("Here could be the Timer.");
		controlInput(new Composite(parent, SWT.FLAT));
		getDurationProvider = timeInput(new Composite(parent, SWT.FLAT));
		timer = new Timer(getDurationProvider, clockLine);
		
		// updateTest();
	}
	
	private void controlInput(Composite parent) {
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		parent.setLayout(fillLayout);
		Button startButton = new Button(parent, SWT.PUSH);
		startButton.setText("start");
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Starte Timer");
				timer.start();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Starte Timer");
				timer.start();
				
			}
		});
		Button clearButton = new Button(parent, SWT.PUSH);
		clearButton.setText("clear");
		clearButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Stoppe Timer");
				timer.clear();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Stoppe Timer");
				timer.clear();	
			}
		});
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
	
	
	
	private void updateTest() {
		clockLine.setText("" + 0);
		RepeatableTask task = new RepeatableTask() {
			@Override
			public void run() {
				int current = Integer.valueOf(clockLine.getText());
				current += 1;
				clockLine.setText("" + current);
				System.out.println("Text should now be " + current);
			}
		};
		RepeatedExecution executer = new RepeatedExecution(1000);
		executer.setTask(task);
		executer.go();
	}

	@Focus
	public void setFocus() {
		clockLine.setFocus();

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1)
				setSelection(iss.getFirstElement());
			else
				setSelection(iss.toArray());
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("Current single selection class is : " + o.getClass());
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("This is a multiple selection of " + selectedObjects.length + " objects");
	}
}
