package com.example.e4.rcp.todo.web.views;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.example.e4.rcp.todo.event.EventConstants;
import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

public class TodoOverviewPart {

	private final IEventBroker eventBroker;

	private final Field.ValueChangeListener listener = new Field.ValueChangeListener() {

		private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(ValueChangeEvent event) {
			if (eventBroker != null) {
				eventBroker.post(EventConstants.TOPIC_TODO_DATA_SELECTED, event
						.getProperty().getValue());
			}
		}
	};

	@Inject
	public TodoOverviewPart(ComponentContainer parent, ITodoModel model,
			IEventBroker eventBroker) {
		this.eventBroker = eventBroker;

		parent.addComponent(new Label("First View"));

		BeanItemContainer<Todo> container = new BeanItemContainer<Todo>(
				Todo.class, model.getTodos());
		ComboBox combo = new ComboBox("To-dos", container);
		combo.setItemCaptionPropertyId(Todo.FIELD_SUMMARY);
		combo.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		combo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		combo.setImmediate(true);
		parent.addComponent(combo);

		combo.addListener(listener);
	}
}
