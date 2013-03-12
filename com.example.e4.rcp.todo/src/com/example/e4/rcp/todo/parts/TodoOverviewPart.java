package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.example.e4.rcp.todo.addons.Injectable;
import com.example.e4.rcp.todo.model.ITodoModel;

public class TodoOverviewPart {
	private Label label;

	@PostConstruct
	public void createControls(Composite parent, Injectable injectable,
			ITodoModel model) {
		label = new Label(parent, SWT.NONE);
		label.setText(getClass().getCanonicalName());

		injectable.sayHello();

		System.out.println(model.getTodos().size());
	}

	@Focus
	public void onFocus() {
		label.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}

}