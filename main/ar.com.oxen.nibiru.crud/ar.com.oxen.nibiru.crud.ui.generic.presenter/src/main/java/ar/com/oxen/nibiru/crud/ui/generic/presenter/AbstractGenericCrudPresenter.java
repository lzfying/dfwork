package ar.com.oxen.nibiru.crud.ui.generic.presenter;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationCallback;
import ar.com.oxen.nibiru.crud.manager.api.BeforeSaveEntityEvent;
import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.manager.api.EditCrudEntityEvent;
import ar.com.oxen.nibiru.crud.manager.api.ModifiedCrudEntityEvent;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionTracker;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public abstract class AbstractGenericCrudPresenter<V extends View, T> extends
		AbstractPresenter<V> {
	private CrudManager<T> crudManager;
	private Conversation conversation;
	private ExtensionPointManager extensionPointManager;
	private AuthorizationService authorizationService;
	private Profile profile;
	
	public AbstractGenericCrudPresenter(CrudManager<T> crudManager,
			EventBus eventBus, Conversation conversation,
			ExtensionPointManager extensionPointManager,
			AuthorizationService authorizationService,
			Profile profile) {
		super(eventBus);
		this.crudManager = crudManager;
		this.conversation = conversation;
		this.extensionPointManager = extensionPointManager;
		this.authorizationService = authorizationService;
		this.profile = profile;
	}

	protected void performGlobalAction(final CrudAction action,
			final CrudActionExtension<T> actionExtension) {
		this.getConversation().execute(new ConversationCallback<Void>() {
			@Override
			public Void doInConversation(Conversation conversation)
					throws Exception {
				CrudEntity<?> returnedEntity = actionExtension
						.performGlobalAction(action);
				processReturnedEntity(returnedEntity);
				return null;
			}
		});
	}

	protected void performEntityAction(final CrudAction action,
			final CrudEntity<T> entity,
			final CrudActionExtension<T> actionExtension) {
		this.getConversation().execute(new ConversationCallback<Void>() {
			@Override
			public Void doInConversation(Conversation conversation)
					throws Exception {
				
				if (action.modifiesEntity() && entity != null) {
					getEventBus().fireEvent(
							new BeforeSaveEntityEvent(entity),
							crudManager.getEntityTypeName());
				}

				CrudEntity<?> returnedEntity = actionExtension
						.performEntityAction(action, entity);
				/*
				 * If the returned entity is the same of original entity, it is
				 * still being modified.
				 */
				if (action.modifiesEntity() && entity != null
						&& !entity.equals(returnedEntity)) {
					getEventBus().fireEvent(
							new ModifiedCrudEntityEvent(entity.getId()),
							crudManager.getEntityTypeName());
				}

				processReturnedEntity(returnedEntity);
				return null;
			}
		});

	}

	private <X> void processReturnedEntity(CrudEntity<X> returnedEntity) {
		if (returnedEntity != null) {
			this.onReturnedEntity(returnedEntity);
			this.getEventBus().fireEvent(
					new EditCrudEntityEvent<X>(returnedEntity,
							this.getConversation()),
					this.crudManager.getEntityTypeName());
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void registerExtensionTracker(
			ExtensionTracker<CrudActionExtension<T>> tracker) {
		this.getExtensionPointManager().registerTracker(tracker,
				this.getTopic(), CrudActionExtension.class);
	}

	protected abstract <K> void onReturnedEntity(CrudEntity<K> returnedEntity);

	protected CrudManager<T> getCrudManager() {
		return crudManager;
	}

	protected String getTopic() {
		return this.crudManager.getEntityTypeName();
	}

	protected ExtensionPointManager getExtensionPointManager() {
		return extensionPointManager;
	}

	protected Conversation getConversation() {
		return conversation;
	}

	protected AuthorizationService getAuthorizationService() {
		return authorizationService;
	}
	
	protected Profile getProfile() {
		return profile;
	}
	
}
