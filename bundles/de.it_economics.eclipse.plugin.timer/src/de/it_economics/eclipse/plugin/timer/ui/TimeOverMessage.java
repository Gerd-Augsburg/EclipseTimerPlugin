package de.it_economics.eclipse.plugin.timer.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.it_economics.eclipse.plugin.timer.Timer;
import de.it_economics.eclipse.plugin.timer.utils.NotifcationObject;

public class TimeOverMessage {
	@Inject
	private IEventBroker eventbroker;
	
	@Inject
	@Optional
	private void timeIsOverNotification(@UIEventTopic(Timer.EVENT_TIME_IS_OVER) NotifcationObject no) {
		Display display = Display.getDefault();
		Shell parentShell = display.getActiveShell();
		String dialogTitle = "Time over";
		Image dialogTitleImage = null;
		String dialogMessage = "Time is over, next one please!";
		int dialogImageType = MessageDialog.INFORMATION;
		String[] dialogButtonLabels = new String[] { "restart", "OK / Stop" };
		int defaultIndex = 0;
		
		MessageDialog dialog = new MessageDialog(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		
		display.asyncExec(() -> {
			int choice = dialog.open();
			if (choice == 0) {
				eventbroker.post(Timer.EVENT_START_TIMER, NotifcationObject.get());
			}
		});
	}
}
