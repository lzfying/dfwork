package ar.com.oxen.nibiru.conversation.api;

/**
 * Service used to access current active conversation.
 */
public interface ConversationAccessor {
	/**
	 * @return The current conversation
	 */
	Conversation getCurrentConversation();
}
