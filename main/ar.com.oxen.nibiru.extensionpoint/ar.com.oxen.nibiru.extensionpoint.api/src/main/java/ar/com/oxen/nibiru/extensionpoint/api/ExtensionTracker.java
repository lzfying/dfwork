package ar.com.oxen.nibiru.extensionpoint.api;

/**
 * Callback for tracking extension status.
 * 
 * @param <T>
 *            The extension type
 */
public interface ExtensionTracker<T> {
	/**
	 * Callback method called when a new extension is registered.
	 * 
	 * @param extension
	 *            The extension
	 */
	void onRegister(T extension);

	/**
	 * Callback method called when an existing extension is unregistered.
	 * 
	 * @param extension
	 *            The extension
	 */
	void onUnregister(T extension);
}
