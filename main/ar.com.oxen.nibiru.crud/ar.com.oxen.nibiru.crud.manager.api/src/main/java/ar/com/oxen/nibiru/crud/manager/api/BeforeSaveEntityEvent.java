package ar.com.oxen.nibiru.crud.manager.api;

public class BeforeSaveEntityEvent {
	private CrudEntity<?> entity;

	public BeforeSaveEntityEvent(CrudEntity<?> entity) {
		super();
		this.entity = entity;
	}

	public CrudEntity<?> getEntity() {
		return entity;
	}

}
