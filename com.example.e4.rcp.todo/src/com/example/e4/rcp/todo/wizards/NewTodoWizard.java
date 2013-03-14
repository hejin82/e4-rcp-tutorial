package com.example.e4.rcp.todo.wizards;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.wizard.Wizard;

@Creatable
public class NewTodoWizard extends Wizard {

	@Inject
	private NewTodoDetailsWizardPage detailsPage;

	@Inject
	private NewTodoValidateWizardPage validatePage;

	public NewTodoWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(detailsPage);
		addPage(validatePage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
