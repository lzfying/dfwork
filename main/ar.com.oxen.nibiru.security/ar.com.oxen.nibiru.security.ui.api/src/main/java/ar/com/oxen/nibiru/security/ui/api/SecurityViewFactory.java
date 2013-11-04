package ar.com.oxen.nibiru.security.ui.api;


public interface SecurityViewFactory {
	/**
	 * Builds the view for login window.
	 * 
	 * @return The view
	 */
	LoginView createLoginView();

	/**
	 * Creates a vew for changing the password.
	 * 
	 * @return The view
	 */
	ChangePasswordView createChangePasswordView();
}
