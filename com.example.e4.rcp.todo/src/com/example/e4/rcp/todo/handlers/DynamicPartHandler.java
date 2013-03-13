package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

public class DynamicPartHandler {

	@Execute
	public void execute(EPartService partService) {
		MPart part = partService
				.createPart("com.example.e4.rcp.todo.partdescriptor.dynamic");
		partService.showPart(part, PartState.ACTIVATE);
	}
}
