package ar.com.oxen.nibiru.session.api;

/**
 * Component holding session data.
 */
public interface Session {
	/**
	 * Gets an object from session data.
	 * 
	 * @param <T>
	 *            The object type
	 * @param key
	 *            The object key (must be unique)
	 * @return The object
	 */
	<T> T get(String key);

	/**
	 * Puts an object into session data.
	 * 
	 * @param key
	 *            The object key (must be unique)
	 * @param value
	 *            The object
	 */
	void put(String key, Object value);

	/**
	 * Removes an object from session data.
	 * 
	 * @param key
	 *            The object key (must be unique)
	 */
	void remove(String key);

	/**
	 * @return An String identifying the session.
	 */
	String getId();

	/**
	 * @return A mutex that can be used in order to synchronize concurrent
	 *         (threaded) session access
	 */
	Object getMutex();

	/**
	 * Registers a listener for session destruction.
	 * 
	 * @param name
	 *            The callback name (must be unique)
	 * @param callback
	 *            The callback
	 */
	void registerDestructionCallback(String name, Runnable callback);

	/**
	 * @return True if the session is valid
	 */
	boolean isValid();
}
