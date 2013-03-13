package com.example.e4.rcp.todo.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;

public class TodoOverviewPart {

	private Button btnLoadData;
	private WritableList writableList;

	@Inject
	private ITodoModel model;

	@Inject
	private ESelectionService selectionService;

	private TableViewer viewer;
	protected String searchString = "";

	@PostConstruct
	public void createControls(Composite parent, final ITodoModel model,
			EMenuService service, MPart part) {
		parent.setLayout(new GridLayout(1, false));

		btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.setText("Update");

		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateViewer(model);
			}

		});

		Text search = new Text(parent, SWT.SEARCH | SWT.CANCEL
				| SWT.ICON_SEARCH);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		search.setMessage("Filter");
		search.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				searchString = source.getText();
				viewer.refresh();
			}
		});
		search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text source = (Text) e.getSource();
					source.setText("");
				}
				super.widgetSelected(e);
			}
		});
		viewer = new TableViewer(parent, SWT.MULTI);
		Table table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setText("Summary");

		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText("Description");

		viewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary().contains(searchString)
						|| todo.getDescription().contains(searchString);
			}
		});

		writableList = new WritableList(model.getTodos(), Todo.class);
		ViewerSupport.bind(
				viewer,
				writableList,
				BeanProperties.values(new String[] { Todo.FIELD_SUMMARY,
						Todo.FIELD_DESCRIPTION }));

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				selectionService.setSelection(selection.getFirstElement());
			}
		});

		List<MMenu> menus = part.getMenus();
		if (menus.size() == 1) {
			service.registerContextMenu(viewer.getControl(), menus.get(0)
					.getElementId());
		}
	}

	private void updateViewer(final ITodoModel model) {
		if (viewer != null) {
			writableList.clear();
			writableList.addAll(model.getTodos());
		}
	}

	@Focus
	public void onFocus() {
		btnLoadData.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}
}