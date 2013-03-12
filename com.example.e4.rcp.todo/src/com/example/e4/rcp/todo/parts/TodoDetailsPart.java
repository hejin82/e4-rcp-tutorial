package com.example.e4.rcp.todo.parts;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.Todo;

public class TodoDetailsPart {
	private Text text;
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
	public void createControls(Composite parent, EMenuService service,
			EModelService modelService, MApplication application, MPart part) {
		parent.setLayout(new GridLayout(2, false));

		lblSummary = new Label(parent, SWT.NONE);
		lblSummary.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSummary.setText("Summary");

		txtSummary = new Text(parent, SWT.BORDER);
		txtSummary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

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

		List<MMenu> menus = part.getMenus();
		if (menus.size() == 1) {
			service.registerContextMenu(text, menus.get(0).getElementId());
		}

		traverseModel(modelService, application);

		this.initialized = true;
	}

	private void traverseModel(EModelService modelService,
			MApplication application) {
		List<MApplicationElement> elements = modelService.findElements(
				application, null, MApplicationElement.class, null);

		System.out.println("### Application Elements ###");
		for (MApplicationElement mApplicationElement : elements) {
			System.out.println(mApplicationElement);
			System.out.println(mApplicationElement.getElementId());
		}

		System.out.println("---------");
		System.out.println("### Parts ###");

		List<MPart> parts = modelService.findElements(application, null,
				MPart.class, null);
		for (MPart part : parts) {
			System.out.println(part);
			System.out.println(part.getElementId());
			MToolBar toolbar = part.getToolbar();
			if (toolbar != null) {
				List<MToolBarElement> toolbarChildren = toolbar.getChildren();
				System.out.println("\t toolbar:");
				for (MToolBarElement mToolBarElement : toolbarChildren) {
					System.out.println("\t\t" + mToolBarElement.getElementId());
				}
			}
			List<MMenu> menus = part.getMenus();
			if (menus.size() > 0) {
				System.out.println("\t menu:");
			}
			for (MMenu mMenu : menus) {
				System.out.println("\t\t" + mMenu.getElementId());
			}
		}

		MinimalEObjectImpl root = (MinimalEObjectImpl) application;
		TreeIterator<EObject> eAllContents = root.eAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next instanceof MApplicationElement) {
				MApplicationElement element = (MApplicationElement) next;
				System.out.println(element.getElementId());
			} else {
				System.out.println("\t" + next);
			}
		}
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