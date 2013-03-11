package com.example.e4.rcp.todo.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;

public class TestHandler {

	@Execute
	public void execute(
			@Named("com.example.e4.rcp.todo.commandparameter.test.input") String param) {
		System.out.println(getClass().getCanonicalName() + " called with "
				+ param);
	}
}
