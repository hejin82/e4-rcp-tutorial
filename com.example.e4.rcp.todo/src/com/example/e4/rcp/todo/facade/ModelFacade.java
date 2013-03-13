package com.example.e4.rcp.todo.facade;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.example.e4.rcp.todo.event.EventConstants;
import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;

@Creatable
public class ModelFacade implements ITodoModel {

	@Inject
	private ITodoModel model;

	@Inject
	private IEventBroker broker;

	@Inject
	@Optional
	public void onLoadDataRequest(
			@EventTopic(EventConstants.TOPIC_TODO_DATA_LOAD_REQUEST) Todo todo) {
		getTodos();
	}

	@Override
	public List<Todo> getTodos() {
		List<Todo> todos = model.getTodos();
		broker.post(EventConstants.TOPIC_TODO_DATA_LOADED, todos);
		return todos;
	}

	@Override
	public boolean saveTodo(Todo todo) {
		boolean result = model.saveTodo(todo);
		broker.post(EventConstants.TOPIC_TODO_DATA_UPDATE_UPDATED, todo);
		return result;
	}

	@Override
	public Todo getTodo(long id) {
		return model.getTodo(id);
	}

	@Override
	public boolean deleteTodo(long id) {
		Todo todo = model.getTodo(id);
		boolean result = model.deleteTodo(id);
		broker.post(EventConstants.TOPIC_TODO_DATA_UPDATE_DELETE, todo);
		return result;
	}
}
