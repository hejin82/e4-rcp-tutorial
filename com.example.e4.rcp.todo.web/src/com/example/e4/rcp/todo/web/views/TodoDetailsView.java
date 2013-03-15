package com.example.e4.rcp.todo.web.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.example.e4.rcp.todo.event.EventConstants;
import com.example.e4.rcp.todo.model.Todo;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TodoDetailsView {
	private final Label summary = new Label();
	private final Label description = new Label("", Label.CONTENT_TEXT);

	protected Todo todo;
	private final HorizontalLayout layout = new HorizontalLayout();
	private GridLayout grid;

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

	@Inject
	public TodoDetailsView(VerticalLayout parent, IEclipseContext context) {
		layout.setSizeFull();
		parent.addComponent(layout);
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
}