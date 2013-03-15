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
import com.example.e4.rcp.todo.facade.ModelFacade;
import com.example.e4.rcp.todo.model.Todo;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

public class TodoOverviewView {

	private IEventBroker eventBroker;

	private IEclipseContext context;

	private final ComboBox combo;

	private final ModelFacade model;

	private final Field.ValueChangeListener listener = new Field.ValueChangeListener() {

		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(ValueChangeEvent event) {
			if (eventBroker != null && context != null) {
				eventBroker.post(EventConstants.TOPIC_TODO_DATA_SELECTED, event
						.getProperty().getValue());
				context.getParent().set("org.eclipse.ui.selection",
						event.getProperty().getValue());
			}
		}
	};

	private final EventHandler todoRemovedHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Todo) {
				Todo todo = (Todo) data;
				boolean removeItem = combo.removeItem(todo);
				if (removeItem) {
					combo.select(combo.getItemIds().iterator().next());
				}
			}

		}
	};
	private final EventHandler todoAddedHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Todo) {
				combo.removeAllItems();
				BeanItemContainer<Todo> container = new BeanItemContainer<Todo>(
						Todo.class, model.getTodos());
				combo.setContainerDataSource(container);
			}

		}
	};

	@Inject
	public TodoOverviewView(ComponentContainer parent, ModelFacade model) {
		this.model = model;
		parent.addComponent(new Label("To-Do Overview"));

		BeanItemContainer<Todo> container = new BeanItemContainer<Todo>(
				Todo.class, model.getTodos());
		combo = new ComboBox("To-dos", container);
		combo.setItemCaptionPropertyId(Todo.FIELD_SUMMARY);
		combo.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		combo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		combo.setImmediate(true);
		parent.addComponent(combo);

		combo.addListener(listener);
	}

	@PostConstruct
	public void postConstruct(IEventBroker eventBroker, IEclipseContext context) {
		this.eventBroker = eventBroker;
		this.context = context;

		this.eventBroker.subscribe(EventConstants.TOPIC_TODO_DATA_UPDATE_NEW,
				todoAddedHandler);
		this.eventBroker.subscribe(
				EventConstants.TOPIC_TODO_DATA_UPDATE_DELETE,
				todoRemovedHandler);
	}

	@PreDestroy
	public void preDestroy() {
		if (this.eventBroker != null) {
			this.eventBroker.unsubscribe(todoAddedHandler);
			this.eventBroker.unsubscribe(todoRemovedHandler);
		}
	}
}
