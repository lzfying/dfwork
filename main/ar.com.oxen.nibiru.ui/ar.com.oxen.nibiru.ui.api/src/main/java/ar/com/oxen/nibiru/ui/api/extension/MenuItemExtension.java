package ar.com.oxen.nibiru.ui.api.extension;

/**
 * Extension that represents an item on the menu.
 */
public interface MenuItemExtension {
	/**
	 * @return The item name
	 */
	String getName();

	/**
	 * @return The position (lower numbers are shown first)
	 */
	int getPosition();

	/**
	 * Method to be executed when the menu is created.
	 */
	void onClick();
	
	/**
	 * @return Roles which this extension is available
	 */
	 String[] getAllowedRoles();
}
