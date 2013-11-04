package ar.com.oxen.nibiru.crud.manager.api;

import java.util.List;

/**
 * Extension used to add actions to CRUD.
 * 
 * @param <T>
 *            The {@link CrudEntity} type
 */
public interface CrudActionExtension<T> {
	/**
	 * Gets global actions provided by this extension.
	 * 
	 * @return A list with the actions
	 */
	List<CrudAction> getGlobalActions();

	/**
	 * Gets entity actions provided by this extension.
	 * 
	 * @return A list with the actions
	 */
	List<CrudAction> getEntityActions(CrudEntity<T> entity);

	/**
	 * Performs an action over a given entity. The action can create/update an
	 * entity. In that case, such entity is returned, otherwise it returns null.
	 * When a created/updated entity is returned, the CRUD should open a form in
	 * order to edit it. This can be useful, for example, for BPM
	 * implementations that jumps from an activity to another.
	 * 
	 * @param action
	 *            The action entity)
	 * @return The created/updated entity
	 */
	CrudEntity<?> performGlobalAction(CrudAction action);

	/**
	 * Performs an action over a given entity. The action can create/update an
	 * entity. In that case, such entity is returned, otherwise it returns null.
	 * When a created/updated entity is returned, the CRUD should open a form in
	 * order to edit it. This can be useful, for example, for BPM
	 * implementations that jumps from an activity to another.
	 * 
	 * @param action
	 *            The action
	 * @param entity
	 *            The entity (it can be null if the action doesn't require an
	 *            entity)
	 * @return The created/updated entity
	 */
	CrudEntity<?> performEntityAction(CrudAction action, CrudEntity<T> entity);

	/**
	 * @return The allowed roles
	 */
	String[] getAllowedRoles();
}
