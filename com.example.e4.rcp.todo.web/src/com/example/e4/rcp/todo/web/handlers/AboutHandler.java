package com.example.e4.rcp.todo.web.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.vaadin.Application;

public class AboutHandler {
	@Execute
	public void execute(Application vaadinApp) {
		vaadinApp.getMainWindow().showNotification("Your To-do cloud service!");
	}
}
