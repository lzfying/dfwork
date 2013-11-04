package ar.com.oxen.nibiru.sample.loginview;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.MainWindow;
import ar.com.oxen.nibiru.ui.api.view.Panel;
import ar.com.oxen.nibiru.ui.api.view.PasswordField;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.utils.mvp.HasValueI18nDecorator;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class CustomLoginView extends AbstractWindowViewAdapter<MainWindow>
		implements LoginView {
	private TextField<String> userField;
	private PasswordField<String> passwordField;
	private Button loginButton;
	private Label<String> errorLabel;

	public CustomLoginView(ViewFactory viewFactory, MessageSource messageSource) {
		super(viewFactory.buildMainWindow(), viewFactory, messageSource);

		Label<String> titleLabel = viewFactory.buildLabel(String.class);
		String appName = messageSource
				.getMessage("ar.com.oxen.nibiru.app.name");
		if (appName != null) {
			titleLabel.setValue(appName
					+ " - "
					+ messageSource
							.getMessage("ar.com.oxen.nibiru.security.login"));

			/* Set main window title */
			getAdapted().setValue(appName);
		} else {
			titleLabel.setValue(messageSource
					.getMessage("ar.com.oxen.nibiru.security.login"));
		}
		this.getAdapted().addComponent(titleLabel);

		Label<String> customLabel = viewFactory.buildLabel(String.class);
		customLabel.setValue("Custom Login view");
		this.getAdapted().addComponent(customLabel);
		
		Panel panel = viewFactory.buildFormPanel();

		this.userField = viewFactory.buildTextField(String.class);
		this.userField.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.app.user") + ":");
		panel.addComponent(userField);

		this.passwordField = viewFactory.buildPasswordField(String.class);
		this.passwordField.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.app.password") + ":");
		panel.addComponent(passwordField);

		this.loginButton = viewFactory.buildButton();
		this.loginButton.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.security.login"));
		panel.addComponent(loginButton);

		this.errorLabel = viewFactory.buildLabel(String.class);
		panel.addComponent(errorLabel);

		this.getAdapted().addComponent(panel);
	}

	@Override
	public HasValue<String> getUserField() {
		return this.userField;
	}

	@Override
	public HasValue<String> getPasswordField() {
		return this.passwordField;
	}

	@Override
	public HasClickHandler getLoginButton() {
		return this.loginButton;
	}

	@Override
	public HasValue<String> getErrorMessage() {
		return new HasValueI18nDecorator(this.errorLabel,
				this.getMessageSource());
	}
}
