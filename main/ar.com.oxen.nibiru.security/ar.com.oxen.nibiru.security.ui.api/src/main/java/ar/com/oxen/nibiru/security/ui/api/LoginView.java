package ar.com.oxen.nibiru.security.ui.api;

import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public interface LoginView extends View {
	HasValue<String> getUserField();

	HasValue<String> getPasswordField();

	HasClickHandler getLoginButton();

	HasValue<String> getErrorMessage();
}
