package com.example.e4.rcp.todo.parts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PlaygroundPart {
	private Text txtCity;
	private Browser browser;
	private Button btnSearch;
	private Label userLabel;

	@PostConstruct
	public void createControls(final Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		userLabel = new Label(parent, SWT.NONE);
		userLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		new Label(parent, SWT.NONE);

		txtCity = new Text(parent, SWT.BORDER);
		txtCity.setToolTipText("Enter City");
		txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		btnSearch = new Button(parent, SWT.NONE);
		btnSearch.setText("Search");

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = txtCity.getText();
				if (text != null && text.length() > 0) {
					try {
						browser.setUrl("https://maps.google.com/maps?q="
								+ URLEncoder.encode(text, "UTF-8")
								+ "&output=embed");
						browser.redraw();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	@Inject
	@Optional
	public void trackUser(
			@Preference(nodePath = "com.example.e4.rcp.todo", value = "user") String user) {
		if (userLabel != null && !userLabel.isDisposed()) {
			userLabel.setText("Hello, " + user);
		}
	}

	@Focus
	public void onFocus() {
		txtCity.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}
}