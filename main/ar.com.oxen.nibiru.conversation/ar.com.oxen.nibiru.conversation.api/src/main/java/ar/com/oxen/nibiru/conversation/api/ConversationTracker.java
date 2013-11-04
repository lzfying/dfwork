package ar.com.oxen.nibiru.conversation.api;

/**
 * Listener for tracking conversation life cycle.
 * 
 */
public interface ConversationTracker {
	/**
	 * Called when conversation finishes OK.
	 * 
	 * @param conversation
	 *            The finished conversation
	 */
	void onEnd(Conversation conversation);

	/**
	 * Called when conversation is canceled.
	 * 
	 * @param conversation
	 *            The canceled conversation
	 */
	void onCancel(Conversation conversation);
}
