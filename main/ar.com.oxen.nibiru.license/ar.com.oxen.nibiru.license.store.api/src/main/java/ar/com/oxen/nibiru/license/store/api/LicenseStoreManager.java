package ar.com.oxen.nibiru.license.store.api;

/**
 * Manage for loading and saving licenses.
 */
public interface LicenseStoreManager {
	String GENERIC_MODULE = "genericModule";

	/**
	 * Loads a license for a given module.
	 * 
	 * @param module
	 *            The module
	 * @return A String representing the license
	 */
	String loadLicense(String module);

	/**
	 * Saves a license for a given module.
	 * 
	 * @param module
	 *            The module
	 * @param license
	 *            A String representing the license
	 */
	void saveLicense(String module, String license);
}
