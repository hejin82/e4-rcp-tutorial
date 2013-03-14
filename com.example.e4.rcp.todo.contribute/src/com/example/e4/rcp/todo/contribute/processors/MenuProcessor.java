package com.example.e4.rcp.todo.contribute.processors;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;

public class MenuProcessor {

	@Inject
	@Named("org.eclipse.ui.file.menu")
	private MMenu menu;

	@Execute
	public void process() {
		if (menu != null && menu.getChildren() != null) {
			ArrayList<MMenuElement> list = new ArrayList<MMenuElement>();
			for (MMenuElement element : menu.getChildren()) {
				if (element.getLabel() != null
						&& element.getLabel().contains("Exit")) {
					list.add(element);
				}
			}
			menu.getChildren().removeAll(list);

			MDirectMenuItem menuItem = MMenuFactory.INSTANCE
					.createDirectMenuItem();
			menuItem.setLabel("My Exit");
			menuItem.setContributionURI("bundleclass://com.example.e4.rcp.todo.contribute/com.example.e4.rcp.todo.contribute.handlers.ExitHandlerWithCheck");
			menu.getChildren().add(menuItem);
		}
	}
}
