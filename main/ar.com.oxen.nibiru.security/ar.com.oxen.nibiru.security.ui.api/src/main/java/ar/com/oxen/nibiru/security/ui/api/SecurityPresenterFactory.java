package ar.com.oxen.nibiru.security.ui.api;

import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

public interface SecurityPresenterFactory {
	/**
	 * Builds the presenter for login window.
	 * 
	 * @return The presenter
	 */
	Presenter<LoginView> createLoginPresenter();

	/**
	 * Builds the presenter for change password window.
	 * 
	 * @return The presenter
	 */
	Presenter<ChangePasswordView> createChangePasswordPresenter();
}
