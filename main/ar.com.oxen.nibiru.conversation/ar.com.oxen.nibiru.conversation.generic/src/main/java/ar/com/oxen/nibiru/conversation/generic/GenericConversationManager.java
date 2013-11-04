package ar.com.oxen.nibiru.conversation.generic;

import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationAccessor;
import ar.com.oxen.nibiru.conversation.api.ConversationFactory;

public class GenericConversationManager implements ConversationFactory, ConversationAccessor {
	private ThreadLocal<Conversation> currentConversation = new ThreadLocal<Conversation>();

	@Override
	public Conversation buildConversation() {
		return new GenericConversation(this);
	}

	@Override
	public Conversation getCurrentConversation() {
		return this.currentConversation.get();
	}
	
	void setCurrentConversation(Conversation conversation) {
		this.currentConversation.set(conversation);
	}

}
