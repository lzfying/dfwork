package ar.com.oxen.nibiru.crud.manager.api;

/**
 * Represents a field on a {@link CrudEntity}.
 * 
 */
public interface CrudField {
	/**
	 * @return The field name
	 */
	String getName();

	/**
	 * @return The field class
	 */
	Class<?> getType();

	/**
	 * @return Information for showing the field in a list.
	 */
	ListInfo getListInfo();

	/**
	 * @return Information for showing the field in a form.
	 */
	FormInfo getFormInfo();

	/**
	 * Information for showing the field in a list.
	 */
	interface ListInfo {
		/**
		 * Determines a fixed width for the field column.
		 * 
		 * @return The column width
		 */
		int getColumnWidth();

	}

	/**
	 * Information for showing the field in a form.
	 */
	interface FormInfo {
		String GENERAL_TAB = "general";

		/**
		 * Determines how the field should be represented (for example, in a
		 * form).
		 * 
		 * @return An element of widget type enumeration
		 */
		WidgetType getWidgetType();

		/**
		 * @return True if the field can't be modified
		 */
		boolean isReadonly();

		/**
		 * Determines how many characters can be set on the field. Applies only
		 * to widgets which holds Strings.
		 * 
		 * @return The maximum length
		 */
		int getMaxLength();

		/**
		 * Returns the tab name where the widget must be shown.
		 * 
		 * @return The tab name
		 */
		String getTab();

		/**
		 * Returns the possible values for this field
		 * 
		 * @return The tab name
		 */
		String[] getValues();
	}
}
