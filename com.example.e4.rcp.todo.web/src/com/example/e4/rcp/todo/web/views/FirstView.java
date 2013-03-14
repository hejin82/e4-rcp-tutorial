package com.example.e4.rcp.todo.web.views;

import javax.inject.Inject;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;

public class FirstView {

	@Inject
	public FirstView(ComponentContainer parent, ITodoModel model) {
		parent.addComponent(new Label("First View"));

		BeanItemContainer<Todo> container = new BeanItemContainer<Todo>(
				Todo.class, model.getTodos());
		ComboBox combo = new ComboBox("To-dos", container);
		combo.setItemCaptionPropertyId("summary");
		combo.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		combo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
		combo.setImmediate(true);
		parent.addComponent(combo);
	}

}
