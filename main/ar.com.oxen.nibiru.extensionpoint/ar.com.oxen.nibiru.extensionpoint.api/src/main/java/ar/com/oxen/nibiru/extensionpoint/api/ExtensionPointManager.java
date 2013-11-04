package ar.com.oxen.nibiru.extensionpoint.api;

/**
 * Service for managing extensions.
 */
public interface ExtensionPointManager {
	/**
	 * Registers an extension under a name and an interface
	 * 
	 * @param <K>
	 *            The extension point interface
	 * @param extension
	 *            The extension
	 * @param extensionPointName
	 *            The extension point name
	 * @param extensionPointInterface
	 *            The extension point interface
	 */
	<K> void registerExtension(K extension, String extensionPointName,
			Class<K> extensionPointInterface);

	/**
	 * Un-registers an extension.
	 * 
	 * @param extension
	 *            The extension.
	 */
	void unregisterExtension(Object extension);

	/**
	 * Registers a tracker for a given extension type and name.
	 * 
	 * @param <T>
	 *            The type parametrized on the tracker
	 * @param <K>
	 *            The extension point interface
	 * @param tracker
	 *            The tracker
	 * @param extensionPointName
	 *            The extension point name
	 * @param extensionPointInterface
	 *            The extension point interface
	 */
	<T, K extends T> void registerTracker(ExtensionTracker<T> tracker,
			String extensionPointName, Class<K> extensionPointInterface);
}
