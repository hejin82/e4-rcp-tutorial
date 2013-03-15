package com.example.e4.rcp.todo.web.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.example.e4.rcp.todo.event.EventConstants;
import com.example.e4.rcp.todo.facade.ModelFacade;
import com.example.e4.rcp.todo.model.Todo;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TodoDetailsView {

	protected Todo todo;
	private final ModelFacade facade;
	private final HorizontalLayout layout = new HorizontalLayout();
	private GridLayout grid;

	@Inject
	private MDirtyable dirtable;

	private final TextField summary = new TextField();
	private final TextField description = new TextField();

	private final EventHandler todoSelectedHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Todo) {
				setTodo((Todo) data);
			}

		}
	};
	private final EventHandler todoUpdatedHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Todo) {
				insertTodo((Todo) data);
			}

		}
	};

	private final TextChangeListener textChangeListener = new TextChangeListener() {

		private static final long serialVersionUID = 1L;

		@Override
		public void textChange(TextChangeEvent event) {
			if (!dirtable.isDirty()) {
				dirtable.setDirty(true);
			}
		}
	};

	@Inject
	public TodoDetailsView(VerticalLayout parent, IEclipseContext context,
			ModelFacade facade) {
		this.facade = facade;
		layout.setSizeFull();
		parent.addComponent(layout);

		this.summary.setImmediate(true);
		this.description.setImmediate(true);

		this.summary.addListener(textChangeListener);
		this.description.addListener(textChangeListener);
	}

	@PostConstruct
	public void postConstruct(IEventBroker broker) {
		broker.subscribe(EventConstants.TOPIC_TODO_DATA_SELECTED,
				todoSelectedHandler);
		broker.subscribe(EventConstants.TOPIC_TODO_DATA_UPDATE_UPDATED,
				todoUpdatedHandler);
	}

	protected void insertTodo(Todo todo) {
		summary.setPropertyDataSource(new ObjectProperty<String>(todo
				.getSummary(), String.class));
		description.setPropertyDataSource(new ObjectProperty<String>(todo
				.getDescription(), String.class));
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
		if (grid == null) {
			grid = new GridLayout(2, 3);
			int k = 0;
			grid.addComponent(new Label("Summary: "), 0, k);
			summary.setWidth("100%");
			grid.addComponent(summary, 1, k++);

			grid.addComponent(new Label("Description: "), 0, k);
			description.setSizeFull();
			grid.addComponent(description, 1, k);
			layout.addComponent(grid);

			grid.setColumnExpandRatio(0, 20);
			grid.setColumnExpandRatio(1, 80);

			grid.setRowExpandRatio(k, 100);
			grid.setRowExpandRatio(--k, 10);
			if (--k >= 0)
				grid.setRowExpandRatio(k, 10);

			grid.setSizeFull();
		}

		insertTodo(todo);
	}

	@PreDestroy
	public void preDestroy(IEventBroker broker) {
		broker.unsubscribe(todoSelectedHandler);
		broker.unsubscribe(todoUpdatedHandler);
	}

	@Persist
	public void persist() {
		todo.setSummary(summary.getValue().toString());
		todo.setDescription(description.getValue().toString());
		facade.saveTodo(todo);
		dirtable.setDirty(false);
	}
}