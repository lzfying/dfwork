package ar.com.oxen.nibiru.conversation.api;

/**
 * Conversation factory.
 */
public interface ConversationFactory {
	/**
	 * Builds a new conversation.
	 * 
	 * @return The conversation
	 */
	Conversation buildConversation();
}
