package com.example.e4.rcp.todo.wizards;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.parts.TodoDetailsPart;

@Creatable
public class NewTodoDetailsWizardPage extends WizardPage {

	private Todo todo;

	@Inject
	private TodoDetailsPart part;

	@Inject
	private IEclipseContext context;

	public NewTodoDetailsWizardPage() {
		super(NewTodoDetailsWizardPage.class.getName());
		setTitle("New Todo");
		setDescription("Enter the todo data");
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	@Override
	public void dispose() {
		part.dispose();
		super.dispose();
	}

	@Override
	@Inject
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		part.createControls(container);
		part.setTodo(todo);
		setControl(container);
	}
}
