package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.example.e4.rcp.todo.addons.Injectable;
import com.example.e4.rcp.todo.model.ITodoModel;

public class TodoOverviewPart {

	private Button btnLoadData;
	private Label lblNumberOfTodos;

	@PostConstruct
	public void createControls(Composite parent, Injectable injectable,
			final ITodoModel model) {
		parent.setLayout(new GridLayout(2, false));

		btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.setText("Load Data");
		lblNumberOfTodos = new Label(parent, SWT.NONE);
		lblNumberOfTodos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		lblNumberOfTodos.setText("No data");

		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblNumberOfTodos.setText("Number of Todo items "
						+ model.getTodos().size());
				lblNumberOfTodos.update();
			}
		});

		injectable.sayHello();
	}

	@Focus
	public void onFocus() {
		btnLoadData.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}
}