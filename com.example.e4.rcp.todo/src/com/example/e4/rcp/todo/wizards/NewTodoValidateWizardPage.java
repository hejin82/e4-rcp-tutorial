package com.example.e4.rcp.todo.wizards;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

@Creatable
public class NewTodoValidateWizardPage extends WizardPage {

	public NewTodoValidateWizardPage() {
		super(NewTodoValidateWizardPage.class.getName());
		setTitle("Validate");
		setDescription("Check to create the todo item");
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, true);
		container.setLayout(layout);
		Label label = new Label(container, SWT.NONE);
		label.setText("Create the todo");
		final Button button = new Button(container, SWT.CHECK);
		button.setText("Check");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(button.getSelection());
			}
		});
		setControl(container);
	}

}
