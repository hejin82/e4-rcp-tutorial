package com.example.e4.rcp.todo.parts;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

import com.example.e4.rcp.todo.model.Todo;

public class TodoDetailsPart {
	private Text txtSummary;
	private Label lblDescription;
	private Label lblDueDate;
	private Text txtDescription;
	private Composite composite;
	private Label lblSummary;
	private DateTime date;
	private Button btnDone;
	private Todo todo;
	private boolean initialized;

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
		txtSummary.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				todo.setSummary(txtSummary.getText());
			}
		});

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
		txtDescription.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				todo.setDescription(txtDescription.getText());
			}
		});

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

	@Focus
	public void onFocus() {
		txtSummary.setFocus();
	}

	public void setTodo(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		if (todo != null) {
			this.todo = todo;
			update();
		}
	}

	private void update() {
		if (this.todo != null && initialized) {
			txtSummary.setText(this.todo.getSummary());
			txtDescription.setText(this.todo.getDescription());
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.todo.getDueDate());
			date.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
		}
	}

	@PreDestroy
	public void dispose() {
	}
}