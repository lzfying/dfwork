package ar.com.oxen.nibiru.conversation.api;

/**
 * Interface representing a conversation between the user and the application.
 */
public interface Conversation {
	/**
	 * Finishes the conversation OK. Typically, this action is called when a
	 * user clicks an "accept" button in order to confirm database changes, etc.
	 */
	void end();

	/**
	 * Cancels the conversation. Typically called when the user presses a
	 * "cancel" button.
	 */
	void cancel();

	/**
	 * Registers a conversation status tracker.
	 * 
	 * @param tracker
	 *            The tracker
	 */
	void registerTracker(ConversationTracker tracker);

	/**
	 * Activates the conversation and executes the code provided by the
	 * callback. Code called from the callback can access the conversation using
	 * the {@link ConversationAccessor} service.
	 * 
	 * @param <T>
	 *            The type to be returned by the callback
	 * @param callback
	 *            The callback
	 * @return The object returned by the callback
	 */
	<T> T execute(ConversationCallback<T> callback);

	/**
	 * Gets an object from conversation data.
	 * 
	 * @param <T>
	 *            The object type
	 * @param key
	 *            The object key (must be unique)
	 * @return The object
	 */
	<T> T get(String key);

	/**
	 * Puts an object into conversation data.
	 * 
	 * @param key
	 *            The object key (must be unique)
	 * @param value
	 *            The object
	 */
	void put(String key, Object value);

	/**
	 * Removes an object from conversation data.
	 * 
	 * @param key
	 *            The object key (must be unique)
	 */
	void remove(String key);
}
