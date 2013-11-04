package ar.com.oxen.nibiru.crud.manager.api;

public class ModifiedCrudEntityEvent {
	private Object id;

	public ModifiedCrudEntityEvent(Object id) {
		super();
		this.id = id;
	}

	public Object getId() {
		return id;
	}
}
