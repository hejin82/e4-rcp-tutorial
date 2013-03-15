package com.example.e4.rcp.todo.annotation.internal;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;

import com.example.e4.rcp.todo.facade.ModelFacade;
import com.example.e4.rcp.todo.model.Todo;

public class UniqueTodoObjectSupplier extends ExtendedObjectSupplier {

	@Inject
	private ModelFacade model;

	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor,
			boolean track, boolean group) {
		long maxId = -1;
		List<Todo> todos = model.getTodos();
		for (Todo todo : todos) {
			if (todo.getId() > maxId) {
				maxId = todo.getId();
			}
		}
		return new Todo(++maxId, "Checked", "Checked", false, new Date());
	}

}
