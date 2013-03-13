package com.example.e4.rcp.todo.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.example.e4.rcp.todo.model.Todo;

public class NewTodoWizard extends Wizard {

	private final Todo todo;

	public NewTodoWizard(Todo todo) {
		this.todo = todo;
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(new NewTodoDetailsWizardPage(todo));
		addPage(new NewTodoValidateWizardPage());
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
