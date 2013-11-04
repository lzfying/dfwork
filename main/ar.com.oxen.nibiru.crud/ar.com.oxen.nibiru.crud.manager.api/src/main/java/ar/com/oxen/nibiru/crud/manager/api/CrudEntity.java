package ar.com.oxen.nibiru.crud.manager.api;

import java.util.List;

/**
 * Represents an entity instance. This interface is used in order to hide entity
 * implementation. This way, CRUD engine could work over Java beans, BPM
 * processes, etc.
 * 
 * @param <T>
 */
public interface CrudEntity<T> {
	/**
	 * Reads the id value.
	 * 
	 * @return The id
	 */
	Object getId();
	
	/**
	 * Gets the fields to be shown in the entity form.
	 * 
	 * @return A list with the fields
	 */
	List<CrudField> getFormFields();


	/**
	 * Reads a field value.
	 * 
	 * @param field
	 *            The field
	 * @return The value
	 */
	Object getValue(CrudField field);

	/**
	 * Reads a field value.
	 * 
	 * @param fieldName
	 *            The field name
	 * @return The value
	 */
	Object getValue(String fieldName);

	/**
	 * Writes a field value
	 * 
	 * @param field
	 *            The field
	 * @param value
	 *            The value
	 */
	void setValue(CrudField field, Object value);

	/**
	 * Writes a field value
	 * 
	 * @param fieldName
	 *            The field name
	 * @param value
	 *            The value
	 */
	void setValue(String fieldName, Object value);

	/**
	 * Gets the wrapped object.
	 * 
	 * @return The entity object
	 */
	T getEntity();

	/**
	 * Returns the entity type name.
	 * 
	 * The name identifies the kind of entity being handled. This is useful, for
	 * example, in order to determine if a given entity is compatible with a
	 * crud manager.
	 * 
	 * @return The type name.
	 */
	String getEntityTypeName();

	/**
	 * Returns the available values for a given field (for example, for using in
	 * a combo box or a list select)
	 * 
	 * @param field
	 *            The field
	 * @return An iterable for the values
	 */
	Iterable<?> getAvailableValues(CrudField field);

	/**
	 * Returns the available values for a given field (for example, for using in
	 * a combo box or a list select)
	 * 
	 * @param fieldName
	 *            The field name
	 * @return An iterable for the values
	 */
	Iterable<?> getAvailableValues(String fieldName);
}
