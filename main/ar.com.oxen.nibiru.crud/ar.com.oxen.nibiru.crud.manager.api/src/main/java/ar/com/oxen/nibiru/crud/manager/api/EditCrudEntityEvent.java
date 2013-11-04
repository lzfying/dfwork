package ar.com.oxen.nibiru.crud.manager.api;

import ar.com.oxen.nibiru.conversation.api.Conversation;

public class EditCrudEntityEvent<T> {
	private CrudEntity<T> entity;
	private Conversation conversation;

	public EditCrudEntityEvent(CrudEntity<T> entity, Conversation conversation) {
		super();
		this.entity = entity;
		this.conversation = conversation;
	}

	public CrudEntity<T> getCrudEntity() {
		return entity;
	}

	public Conversation getConversation() {
		return conversation;
	}
}
