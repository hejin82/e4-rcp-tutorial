package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

public class ThemeSwitchHandler {

	static boolean defaultTheme = true;

	@Execute
	public void switchTheme(IThemeEngine engine) {
		if (!defaultTheme) {
			engine.setTheme("com.example.e4.rcp.todo.theme.default", true);
		} else {
			engine.setTheme("com.example.e4.rcp.todo.theme.red", true);
		}
		defaultTheme = !defaultTheme;
	}
}
