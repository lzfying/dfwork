package ar.com.oxen.nibiru.conversation.generic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationCallback;
import ar.com.oxen.nibiru.conversation.api.ConversationTracker;

public class GenericConversation implements Conversation {
	private Set<ConversationTracker> trackers = new HashSet<ConversationTracker>();
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private GenericConversationManager conversationManager;

	public GenericConversation(GenericConversationManager conversationManager) {
		super();
		this.conversationManager = conversationManager;
	}

	@Override
	public void end() {
		for (ConversationTracker tracker : this.trackers) {
			tracker.onEnd(this);
		}

		this.trackers.clear();
		this.attributes.clear();
	}

	@Override
	public void cancel() {
		for (ConversationTracker tracker : this.trackers) {
			tracker.onCancel(this);
		}

		this.trackers.clear();
		this.attributes.clear();
	}

	@Override
	public void registerTracker(ConversationTracker tracker) {
		this.trackers.add(tracker);
	}

	@Override
	public <T> T execute(ConversationCallback<T> callback) {
		try {
			Conversation previousConversation = this.conversationManager
					.getCurrentConversation();
			this.conversationManager.setCurrentConversation(this);
			T returnValue = callback.doInConversation(this);
			this.conversationManager
					.setCurrentConversation(previousConversation);
			return returnValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T) this.attributes.get(key);
	}

	@Override
	public void put(String key, Object value) {
		this.attributes.put(key, value);
	}

	@Override
	public void remove(String key) {
		this.attributes.remove(key);
	}
}
