package ar.com.oxen.nibiru.crud.ui.generic.presenter.list;

import java.util.List;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationCallback;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;

public class GenericCrudListPresenterByParent<T> extends
		AbstractGenericCrudListPresenter<T> {
	private String parentField;
	private Object parent;

	public GenericCrudListPresenterByParent(CrudManager<T> crudManager,
			EventBus eventBus, Conversation conversation,
			ExtensionPointManager extensionPointManager, String parentField,
			Object parent, AuthorizationService authorizationService, Profile profile) {
		super(crudManager, eventBus, conversation, extensionPointManager, authorizationService, profile);

		this.parentField = parentField;
		this.parent = parent;
	}

	@Override
	protected List<CrudEntity<T>> findEntities() {
		return this.getConversation().execute(
				new ConversationCallback<List<CrudEntity<T>>>() {
					@Override
					public List<CrudEntity<T>> doInConversation(
							Conversation conversation) throws Exception {
						return getCrudManager().findByfield(
								parentField, parent);
					}
				});
	}

	@Override
	protected <K> void onReturnedEntity(CrudEntity<K> returnedEntity) {
		// TODO: Esto esta medio feo. A toda entidad retornada por una accion
		// que sea compatible con el crud manager le asocio el valor del campo
		// padre. A lo mejor deberia establecerse alguna otra condicion.
		if (returnedEntity.getEntityTypeName().equals(
				this.getCrudManager().getEntityTypeName())) {
			returnedEntity.setValue(this.parentField, this.parent);
		}
	}

	@Override
	protected void customGo() {
	}

	@Override
	protected void onClose() {
	}
}
