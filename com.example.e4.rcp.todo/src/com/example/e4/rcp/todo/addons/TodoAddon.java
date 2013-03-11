package com.example.e4.rcp.todo.addons;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

public class TodoAddon {
	@Inject
	IEventBroker eventBroker;

	@PostConstruct
	void hookListeners(IEclipseContext context) {
		context.set(Injectable.class, new Injectable());
	}

	@PreDestroy
	void unhookListeners() {
		// Unhook event listeners
	}
}