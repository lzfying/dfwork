package ar.com.oxen.nibiru.crud.manager.api;

/**
 * Represents an action that can be applied on a CRUD. Abstracting the actions
 * allows the CRUD implementations to provide extra actions. This way, actions
 * are not limited to create, read, update an delete (so the module shouldn't be
 * called CRUD!!!), but can add action such as approve, reject, start, stop,
 * etc. In some cases, the action can require no entity (for example, "new"). In
 * other cases, it would be mandatory applying the action over an specific
 * {@link CrudEntity} ("edit", for example).
 * 
 */
public interface CrudAction {
	String NEW = "new";
	String DELETE = "delete";
	String EDIT = "edit";
	String UPDATE = "update";

	/**
	 * Gets the action name.
	 * 
	 * @return The name
	 */
	String getName();

	/**
	 * Indicates if the action must be performed over an {@link CrudEntity}.
	 * 
	 * @return True if a {@link CrudEntity} is required
	 */
	boolean isEntityRequired();

	/**
	 * Indicates if a user confirmation must be presented before performing the
	 * action.
	 * 
	 * @return True if confirmation must be presented
	 */
	boolean isConfirmationRequired();

	/**
	 * Indicates if the action must be shown in list window.
	 * 
	 * @return True if it must be shown
	 */
	boolean isVisibleInList();

	/**
	 * Indicates if the action must be shown in form window.
	 * 
	 * @return True if it must be shown
	 */
	boolean isVisibleInForm();
	
	/**
	 * Indicates if the action modifies the entity.
	 * 
	 * @return True if entity is modified
	 */
	boolean modifiesEntity();
	
	String[] getAllowedRoles();
}
