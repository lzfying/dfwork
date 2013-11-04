package ar.com.oxen.nibiru.crud.utils;

import java.util.ArrayList;
import java.util.List;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.ManageChildCrudEntitiesEvent;

public class ManageChildrenCrudActionExtension<T> extends
		AbstractCrudActionExtension<T> {
	private List<CrudAction> actions;

	private String actionName;
	private String parentField;
	private String topic;
	private EventBus eventBus;

	public ManageChildrenCrudActionExtension(String actionName,
			String parentField, String topic, EventBus eventBus) {
		this(actionName, parentField, topic, eventBus, null);
	}

	public ManageChildrenCrudActionExtension(String actionName,
			String parentField, String topic, EventBus eventBus,
			String[] allowedRoles) {
		super(allowedRoles);
		this.actionName = actionName;
		this.parentField = parentField;
		this.eventBus = eventBus;
		this.topic = topic;

		this.actions = new ArrayList<CrudAction>(1);
		this.actions.add(new SimpleCrudAction(this.actionName, true, false,
				true, false, false, null));
	}

	@Override
	public List<CrudAction> getEntityActions(CrudEntity<T> entity) {
		return this.actions;
	}

	@Override
	public CrudEntity<T> performEntityAction(CrudAction action,
			CrudEntity<T> entity) {
		if (this.actionName.equals(action.getName())) {
			this.eventBus.fireEvent(new ManageChildCrudEntitiesEvent(
					this.parentField, entity.getEntity()), topic);
		} else {
			throw new IllegalArgumentException("Invalid action:"
					+ action.getName());
		}
		return null;
	}
}
