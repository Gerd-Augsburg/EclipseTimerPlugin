package de.it_economics.eclipse.plugin.timer;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import de.it_economics.eclipse.plugin.timer.utils.Duration;
import de.it_economics.eclipse.plugin.timer.utils.DurationFormatter;
import de.it_economics.eclipse.plugin.timer.utils.RepeatableTask;
import de.it_economics.eclipse.plugin.timer.utils.RepeatedExecution;

public class Timer {
	private Supplier<Duration> duration;
	private Optional<RepeatableTask> finalAction = Optional.empty();
	private Label outputField;
	private Optional<RepeatedExecution> executorOption = Optional.empty();
	public Timer(Supplier<Duration> durationGetter, Label outputField) {
		duration = durationGetter;
		this.outputField = outputField;	
	}
	public void setFinalAction(RepeatableTask task) {
		finalAction = Optional.ofNullable(task);
	}
	public void start() {
		RepeatedExecution executor = executorOption.orElse(new RepeatedExecution(1000));
		executorOption = Optional.of(executor);
		Duration timerDuration;
		try {
			timerDuration = duration.get();
		} catch (NumberFormatException e) {
			System.out.println("Bad Number format");
			return;
		}
		DurationFormatter formatter = new DurationFormatter(timerDuration);
		System.out.println("Provided Time is " + formatter.getClocklike());
		executor.setTask(new RepeatableTask() {
			
			@Override
			public void run() {
				if (timerDuration.isRealPositiv()) {
					timerDuration.addSeconds(-1);
				} else {
					this.cancel();
					finalAction.ifPresent(RepeatableTask::run);
				}
				updateUi(formatter.getClocklike());
			}
		});
		executor.go();
	}
	public void clear() {
		executorOption.ifPresent(RepeatedExecution::cancelRepetition);
	}
	private void updateUi(String s) {
		Display.getDefault().asyncExec(() -> outputField.setText(s));
	}
	
}
