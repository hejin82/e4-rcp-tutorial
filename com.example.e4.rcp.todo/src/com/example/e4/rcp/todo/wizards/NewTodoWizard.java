package com.example.e4.rcp.todo.wizards;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.wizard.Wizard;

import com.example.e4.rcp.todo.model.Todo;

@Creatable
public class NewTodoWizard extends Wizard {

	@Inject
	private NewTodoDetailsWizardPage detailsPage;

	public NewTodoWizard() {
		setWindowTitle("New Wizard");
	}

	public void setTodo(Todo todo) {
		detailsPage.setTodo(todo);
	}

	@Override
	public void addPages() {
		addPage(detailsPage);
		addPage(new NewTodoValidateWizardPage());
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
