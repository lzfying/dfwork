package ar.com.oxen.nibiru.conversation.api;

/**
 * Conversation callback. Used to run code that can access the active
 * conversation using {@link ConversationAccessor}.
 * 
 * @param <T>
 */
public interface ConversationCallback<T> {
	/**
	 * Method to be executed when conversation is activated.
	 * 
	 * @param conversation
	 *            The active conversation
	 * @return Anything that the callback would want to return
	 * @throws Exception
	 *             At any error
	 */
	T doInConversation(Conversation conversation) throws Exception;
}
