package ar.com.oxen.nibiru.crud.ui.generic.presenter.list;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.eventbus.api.EventHandler;
import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationCallback;
import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.manager.api.ModifiedCrudEntityEvent;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView.EntityActionDefinition;
import ar.com.oxen.nibiru.crud.ui.generic.presenter.AbstractGenericCrudPresenter;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionTracker;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.CloseHandler;

public abstract class AbstractGenericCrudListPresenter<T> extends
		AbstractGenericCrudPresenter<CrudListView, T> {
	private EventHandler<ModifiedCrudEntityEvent> modifiedEventHandler;
	private List<CrudEntity<T>> entities = new LinkedList<CrudEntity<T>>();
	private Set<CrudActionExtension<T>> actionExtensions = new HashSet<CrudActionExtension<T>>();

	public AbstractGenericCrudListPresenter(CrudManager<T> crudManager,
			EventBus eventBus, Conversation conversation,
			ExtensionPointManager extensionPointManager,
			AuthorizationService authorizationService, Profile profile) {
		super(crudManager, eventBus, conversation, extensionPointManager,
				authorizationService, profile);
	}

	private boolean isAllowedRole(String[] roles) {
		if (roles == null || roles.length == 0) {
			return true;
		}
		for (String role : roles) {
			if (getAuthorizationService().isCallerInRole(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void go() {
		this.getView().setEntityName(this.getCrudManager().getEntityTypeName());

		this.configureClose(this.getView());

		this.registerExtensionTracker(new ExtensionTracker<CrudActionExtension<T>>() {

			@Override
			public void onRegister(CrudActionExtension<T> extension) {
				String[] roles = extension.getAllowedRoles();
				if (isAllowedRole(roles)) {
					actionExtensions.add(extension);
					addActions(extension);
				}
			}

			@Override
			public void onUnregister(CrudActionExtension<T> extension) {
				actionExtensions.remove(extension);
				// TODO remover acciones
			}

		});

		this.configureEntityActions();

		this.refreshData();

		this.modifiedEventHandler = new EventHandler<ModifiedCrudEntityEvent>() {
			@Override
			public void onEvent(ModifiedCrudEntityEvent event) {
				refreshEntity(event.getId());
			}
		};

		this.getEventBus().addHandler(ModifiedCrudEntityEvent.class,
				this.modifiedEventHandler, this.getTopic());
		
		this.getView().getCloseHander().setCloseHandler(new CloseHandler() {
			@Override
			public void onClose() {
				getEventBus().removeHandler(modifiedEventHandler);
				AbstractGenericCrudListPresenter.this.onClose();
			}
		});

		this.customGo();
	}
	
	private void refreshData() {
		this.entities.clear();

		for (CrudEntity<T> entity : this.findEntities()) {
			this.entities.add(entity);
		}

		this.refreshTable();
	}

	private void refreshEntity(final Object id) {
		if (id != null) {
			CrudEntity<T> entity = this.getConversation().execute(
					new ConversationCallback<CrudEntity<T>>() {

						@Override
						public CrudEntity<T> doInConversation(
								Conversation conversation) throws Exception {
							return getCrudManager().findById(id);
						}
					});

			if (entity != null) {
				boolean found = false;
				for (int n = 0; n < this.entities.size(); n++) {
					if (this.entities.get(n).getId().equals(id)) {
						this.entities.set(n, entity);
						found = true;
						break;
					}
				}
				
				if (!found) {
					this.entities.add(entity);
				}
				
				this.refreshTable();
			} else {
				/*
				 * If it is not found, maybe the entity has been deleted.
				 * Refresh the full table.
				 */
				this.refreshData();
			}
		} else {
			this.refreshData();
		}
	}

	private void refreshTable() {
		this.getView().clearTable();
		List<CrudField> fields = this.getConversation().execute(
				new ConversationCallback<List<CrudField>>() {
					@Override
					public List<CrudField> doInConversation(
							Conversation conversation) throws Exception {
						return getCrudManager().getListFields();
					}
				});
		for (CrudField field : fields) {
			this.getView().addColumn(field.getName(), field.getType(),
					field.getListInfo().getColumnWidth());
		}

		for (CrudEntity<?> entity : this.entities) {
			Object[] values = new Object[fields.size()];
			for (int n = 0; n < values.length; n++) {
				values[n] = entity.getValue(fields.get(n));
			}
			this.getView().addRow(values);
		}

	}

	protected abstract List<CrudEntity<T>> findEntities();

	protected abstract void customGo();

	protected abstract void onClose();

	private void addActions(final CrudActionExtension<T> extension) {
		for (final CrudAction action : extension.getGlobalActions()) {
			this.addGlobalAction(action, extension);
		}
	}

	private void configureEntityActions() {
		this.getView().setEntitySelectedHandler(new ClickHandler() {
			@Override
			public void onClick() {
				final CrudEntity<T> selectedEntity = entities.get(getView()
						.getSelectedRow());
				List<EntityActionDefinition> actionDefinitions = new LinkedList<EntityActionDefinition>();

				for (CrudActionExtension<T> extension : actionExtensions) {
					for (final CrudAction action : extension
							.getEntityActions(selectedEntity)) {
						addEntityAction(action, actionDefinitions,
								selectedEntity, extension);
					}
				}

				getView().showEntityActions(actionDefinitions);
			}
		});

	}

	private void addGlobalAction(final CrudAction action,
			final CrudActionExtension<T> extension) {
		String[] roles = action.getAllowedRoles();
		if (isAllowedRole(roles)) {
			if (action.isVisibleInList()) {
				this.getView().addGlobalAction(action.getName(),
						action.isConfirmationRequired(), new ClickHandler() {
							@Override
							public void onClick() {
								performGlobalAction(action, extension);
							}
						});
			}
		}
	}

	private void addEntityAction(final CrudAction action,
			List<EntityActionDefinition> actionDefinitions,
			final CrudEntity<T> selectedEntity,
			final CrudActionExtension<T> extension) {
		String[] roles = action.getAllowedRoles();
		if (isAllowedRole(roles)) {
			if (action.isVisibleInList()) {
				actionDefinitions.add(new EntityActionDefinition(action
						.getName(), action.isConfirmationRequired(),
						new ClickHandler() {
							@Override
							public void onClick() {
								performEntityAction(action, selectedEntity,
										extension);
							}
						}));

			}
		}
	}
}
