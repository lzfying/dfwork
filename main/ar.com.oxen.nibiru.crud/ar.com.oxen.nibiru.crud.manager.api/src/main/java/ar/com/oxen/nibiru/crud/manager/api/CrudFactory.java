package ar.com.oxen.nibiru.crud.manager.api;

/**
 * A factory for CRUD components.
 */
public interface CrudFactory {
	/**
	 * Creates a CRUD manager.
	 * 
	 * @param persistentClass
	 *            The class to be managed
	 * @return The CRUD manager
	 */
	<T> CrudManager<T> createCrudManager(Class<T> persistentClass);

	/**
	 * Creates the default extension.
	 * 
	 * @param persistentClass
	 *            The class over which the extension will apply
	 * @return The extension
	 */
	<T> CrudActionExtension<T> createDefaultCrudActionExtension(
			Class<T> persistentClass);
}
