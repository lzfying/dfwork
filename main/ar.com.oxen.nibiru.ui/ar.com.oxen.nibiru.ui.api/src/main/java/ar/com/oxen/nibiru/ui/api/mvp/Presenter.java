package ar.com.oxen.nibiru.ui.api.mvp;

/**
 * A presenter. The presenter should contain the presentation logic, in order to
 * keep it decoupled from the view.
 * 
 * @param <V>
 *            The view type.
 */
public interface Presenter<V extends View> {
	/**
	 * Activates the presenter. This method is called after setting the view.
	 * Typically, this method will add listeners for presentation logic that
	 * reacts to view events.
	 */
	void go();

	/**
	 * @param view
	 *            The view to be used with the presenter
	 */
	void setView(V view);
}
