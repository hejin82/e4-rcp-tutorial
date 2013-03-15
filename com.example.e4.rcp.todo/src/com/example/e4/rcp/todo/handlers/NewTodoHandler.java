package com.example.e4.rcp.todo.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.annotation.UniqueTodo;
import com.example.e4.rcp.todo.facade.ModelFacade;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.wizards.NewTodoWizard;

public class NewTodoHandler {

	@Inject
	@UniqueTodo
	private Todo todo;

	@Execute
	public void execute(Shell shell, ModelFacade model,
			IEclipseContext parentContext, NewTodoWizard wizard) {
		IEclipseContext context = parentContext.createChild();
		context.set(Todo.class, todo);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		if (dialog.open() == WizardDialog.OK) {
			model.saveTodo(todo);
		}
	}
}
