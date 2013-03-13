package com.example.e4.rcp.todo.handlers;

import java.util.Date;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.wizards.NewTodoWizard;

public class NewTodoHandler {

	@Execute
	public void execute(Shell shell, ITodoModel model, NewTodoWizard wizard) {
		Todo todo = new Todo();
		todo.setDueDate(new Date());
		wizard.setTodo(todo);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		if (dialog.open() == WizardDialog.OK) {
			model.saveTodo(todo);
		}
	}
}
