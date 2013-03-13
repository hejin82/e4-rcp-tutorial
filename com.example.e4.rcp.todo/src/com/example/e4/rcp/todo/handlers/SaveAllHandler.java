package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveAllHandler {

	@CanExecute
	public boolean canExecute(@Optional EPartService partService) {
		return partService != null && !partService.getDirtyParts().isEmpty();
	}

	@Execute
	public void execute(EPartService partService) {
		partService.saveAll(false);
	}
}
