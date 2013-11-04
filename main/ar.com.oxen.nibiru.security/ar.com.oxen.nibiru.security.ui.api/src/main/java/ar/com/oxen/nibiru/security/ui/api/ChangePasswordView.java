package ar.com.oxen.nibiru.security.ui.api;

import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public interface ChangePasswordView extends View {
	HasValue<String> getOldPassword();

	HasValue<String> getNewPassword();

	HasValue<String> getPasswordConfirmation();

	HasClickHandler getChangePassword();

	void notifyBadOldPassword();

	void notifyPasswordMismatch();
}
