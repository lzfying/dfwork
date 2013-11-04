package ar.com.oxen.nibiru.crud.utils;

import java.util.ArrayList;
import java.util.List;

import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;

public class AbstractCrudActionExtension<T> implements CrudActionExtension<T> {
	private String[] allowedRoles;

	public AbstractCrudActionExtension(String[] allowedRoles) {
		super();
		this.allowedRoles = allowedRoles;
	}

	@Override
	public List<CrudAction> getGlobalActions() {
		return new ArrayList<CrudAction>();
	}

	@Override
	public List<CrudAction> getEntityActions(CrudEntity<T> entity) {
		return new ArrayList<CrudAction>();
	}

	@Override
	public CrudEntity<?> performGlobalAction(CrudAction action) {
		throw new IllegalArgumentException("Invalid action:" + action.getName());
	}

	@Override
	public CrudEntity<?> performEntityAction(CrudAction action,
			CrudEntity<T> entity) {
		throw new IllegalArgumentException("Invalid action:" + action.getName());
	}

	@Override
	public String[] getAllowedRoles() {
		return this.allowedRoles;
	}
}
