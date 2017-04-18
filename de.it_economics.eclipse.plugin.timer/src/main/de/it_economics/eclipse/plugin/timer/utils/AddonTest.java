
package de.it_economics.eclipse.plugin.timer.utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

public class AddonTest {

	@Inject
	@Optional
	public void applicationStarted(@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
		System.out.println("Test is loaded");
	}
	
	@PostConstruct
    public void init(IEclipseContext context) {
            // injected IEclipseContext comes from the application
            System.out.println("Test is in post construct");
    }

}
