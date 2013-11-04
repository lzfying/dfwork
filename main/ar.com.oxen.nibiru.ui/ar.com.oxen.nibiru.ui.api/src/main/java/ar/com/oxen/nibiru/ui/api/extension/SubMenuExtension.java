package ar.com.oxen.nibiru.ui.api.extension;

/**
 * Extension that represents a menu that can contain other menus.
 */
public interface SubMenuExtension {
	/**
	 * @return The sub-menu name
	 */
	String getName();

	/**
	 * @return The position (lower numbers are shown first)
	 */
	int getPosition();

	/**
	 * @return The extension point name where entries of this sub-menu should be
	 *         added.
	 */
	String getExtensionPoint();

	/**
	 * @return Roles which this extension is available
	 */
	 String[] getAllowedRoles();

}
