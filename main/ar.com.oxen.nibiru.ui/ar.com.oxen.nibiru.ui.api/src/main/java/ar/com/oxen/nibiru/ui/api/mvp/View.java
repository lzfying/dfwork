package ar.com.oxen.nibiru.ui.api.mvp;

/**
 * A view. Implementations of this interface shouldn't contain presentation
 * logic. Instead, display-related logic, such as layout setup, text
 * internationalization, etc should be responsibility of View implementations.
 * 
 */
public interface View {
	/**
	 * Shows the view.
	 */
	void show();

	/**
	 * Closes the view.
	 */
	void close();
}
