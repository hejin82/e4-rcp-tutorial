package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.facade.ModelFacade;
import com.example.e4.rcp.todo.model.Todo;

@Creatable
public class TodoDetailsPart {
	private final DataBindingContext ctx = new DataBindingContext();
	private Button btnDone;
	private Composite composite;
	private DateTime date;
	private boolean initialized;
	private Label lblDescription;
	private Label lblDueDate;
	private Label lblSummary;
	private Todo todo;
	private Text txtDescription;
	private Text txtSummary;

	@Inject
	MDirtyable dirty;

	private final IChangeListener listener = new IChangeListener() {

		@Override
		public void handleChange(ChangeEvent event) {
			dirty.setDirty(true);
		}
	};

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		lblSummary = new Label(parent, SWT.NONE);
		lblSummary.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSummary.setText("Summary");

		txtSummary = new Text(parent, SWT.BORDER);
		txtSummary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		ControlDecoration decSummary = new ControlDecoration(txtSummary,
				SWT.TOP | SWT.LEFT);
		Image imgInfo = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
				.getImage();
		decSummary.setDescriptionText("This is the todo summary");
		decSummary.setImage(imgInfo);
		decSummary.setShowOnlyOnFocus(true);

		lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescription.setText("Description");

		txtDescription = new Text(parent, SWT.BORDER | SWT.MULTI);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDueDate.setText("Due Date");

		date = new DateTime(parent, SWT.BORDER);

		new Label(parent, SWT.NONE);

		composite = new Composite(parent, SWT.NONE);

		btnDone = new Button(composite, SWT.CHECK);
		btnDone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnDone.setBounds(0, 10, 93, 18);
		btnDone.setText("Done");

		this.initialized = true;
	}

	@PreDestroy
	public void dispose() {
		unbind();
		initialized = false;
	}

	@Focus
	public void onFocus() {
		txtSummary.setFocus();
	}

	@Persist
	public void save(MDirtyable dirty, ModelFacade model) {
		model.saveTodo(todo);
		dirty.setDirty(false);
	}

	@Inject
	public void setTodo(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		unbind();
		this.todo = todo;
		if (this.todo != null) {
			if (initialized) {
				bind();
			}
		} else {
			if (initialized) {
				txtSummary.setText("");
				txtDescription.setText("");
				btnDone.setSelection(false);
			}
		}
	}

	private void bind() {
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(
				txtSummary);
		IObservableValue model = PojoProperties.value(Todo.FIELD_SUMMARY)
				.observe(todo);
		ctx.bindValue(target, model);

		target = WidgetProperties.text(SWT.Modify).observe(txtDescription);
		model = PojoProperties.value(Todo.FIELD_DESCRIPTION).observe(todo);
		ctx.bindValue(target, model);

		target = WidgetProperties.selection().observe(btnDone);
		model = PojoProperties.value(Todo.FIELD_DONE).observe(todo);
		ctx.bindValue(target, model);

		target = WidgetProperties.selection().observe(date);
		model = PojoProperties.value(Todo.FIELD_DUEDATE).observe(todo);
		ctx.bindValue(target, model);

		IObservableList providers = ctx.getValidationStatusProviders();
		for (Object object : providers) {
			Binding b = (Binding) object;
			b.getTarget().addChangeListener(listener);
		}
	}

	private void unbind() {
		dirty.setDirty(false);

		IObservableList providers = ctx.getValidationStatusProviders();
		for (Object object : providers) {
			Binding b = (Binding) object;
			b.getTarget().removeChangeListener(listener);
		}
		ctx.dispose();
	}
}