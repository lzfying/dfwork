package ar.com.oxen.nibiru.security.ui.generic.view;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.security.ui.api.ChangePasswordView;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.PasswordField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class GenericChangePasswordView extends
		AbstractWindowViewAdapter<Window> implements ChangePasswordView {
	private PasswordField<String> oldPassword;
	private PasswordField<String> newPassword;
	private PasswordField<String> passwordConfirmation;
	private Button changePassword;
	private Label<String> errorLabel;

	public GenericChangePasswordView(ViewFactory viewFactory,
			MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);
		this.getTitle()
				.setValue(
						messageSource
								.getMessage("ar.com.oxen.nibiru.security.passwordChange"));

		this.oldPassword = viewFactory.buildPasswordField(String.class);
		this.oldPassword.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.security.oldPassword"));
		this.getAdapted().addComponent(this.oldPassword);

		this.newPassword = viewFactory.buildPasswordField(String.class);
		this.newPassword.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.security.newPassword"));
		this.getAdapted().addComponent(this.newPassword);

		this.passwordConfirmation = viewFactory
				.buildPasswordField(String.class);
		this.passwordConfirmation
				.setCaption(messageSource
						.getMessage("ar.com.oxen.nibiru.security.passwordConfirmation"));
		this.getAdapted().addComponent(this.passwordConfirmation);

		this.changePassword = viewFactory.buildButton();
		this.changePassword.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.security.changePassword"));
		this.getAdapted().addComponent(this.changePassword);

		this.errorLabel = viewFactory.buildLabel(String.class);
		this.getAdapted().addComponent(this.errorLabel);
	}

	@Override
	public HasValue<String> getOldPassword() {
		return this.oldPassword;
	}

	@Override
	public HasValue<String> getNewPassword() {
		return this.newPassword;
	}

	@Override
	public HasValue<String> getPasswordConfirmation() {
		return this.passwordConfirmation;
	}

	@Override
	public HasClickHandler getChangePassword() {
		return this.changePassword;
	}

	@Override
	public void notifyBadOldPassword() {
		this.showError("ar.com.oxen.nibiru.security.invalidOldPassword");
	}

	@Override
	public void notifyPasswordMismatch() {
		this.showError("ar.com.oxen.nibiru.security.passwordMismatch");
	}

	private void showError(String code) {
		String message = this.getMessageSource().getMessage(code);
		this.errorLabel.setValue(message);
	}
}
