package ar.com.oxen.nibiru.crud.manager.api;

/**
 * This is a generic event class for triggering entities management related to a
 * parent. The topic should be used in order to identify the entity to be
 * managed.
 */
public class ManageChildCrudEntitiesEvent {
	private String parentField;
	private Object parentEntity;

	public ManageChildCrudEntitiesEvent(String parentField, Object parentEntity) {
		super();
		this.parentField = parentField;
		this.parentEntity = parentEntity;
	}

	public String getParentField() {
		return parentField;
	}

	public Object getParentEntity() {
		return parentEntity;
	}
}
