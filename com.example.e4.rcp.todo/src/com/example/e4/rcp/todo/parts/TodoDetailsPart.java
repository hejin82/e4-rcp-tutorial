package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TodoDetailsPart {
	private Label label;
	private Text text;

	@PostConstruct
	public void createControls(Composite parent, EMenuService service) {
		label = new Label(parent, SWT.NONE);
		label.setText(getClass().getCanonicalName());

		text = new Text(parent, SWT.NONE);
		text.setText("Foo bar");
		service.registerContextMenu(text,
				"com.example.e4.rcp.todo.popupmenu.table");
	}

	@Focus
	public void onFocus() {
		label.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}
}