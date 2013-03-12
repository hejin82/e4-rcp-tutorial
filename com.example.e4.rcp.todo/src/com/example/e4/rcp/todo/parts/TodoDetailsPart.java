package com.example.e4.rcp.todo.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TodoDetailsPart {
	private Label label;
	private Text text;

	@PostConstruct
	public void createControls(Composite parent, EMenuService service,
			EModelService modelService, MApplication application, MPart part) {
		label = new Label(parent, SWT.NONE);
		label.setText(getClass().getCanonicalName());

		text = new Text(parent, SWT.NONE);
		text.setText("Foo bar");

		List<MMenu> menus = part.getMenus();
		if (menus.size() == 1) {
			service.registerContextMenu(text, menus.get(0).getElementId());
		}

		traverseModel(modelService, application);
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
		label.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}
}