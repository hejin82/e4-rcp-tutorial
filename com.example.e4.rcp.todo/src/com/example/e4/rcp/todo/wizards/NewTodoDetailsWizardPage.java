package com.example.e4.rcp.todo.wizards;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.parts.TodoDetailsPart;

public class NewTodoDetailsWizardPage extends WizardPage {

	private final Todo todo;

	public NewTodoDetailsWizardPage(Todo todo) {
		super(NewTodoDetailsWizardPage.class.getName());
		this.todo = todo;
		setTitle("New Todo");
		setDescription("Enter the todo data");
	}

	@Override
	@Inject
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		TodoDetailsPart part = new TodoDetailsPart();
		part.createControls(container);
		part.setTodo(todo);
		setControl(container);
	}

}
