package ar.com.oxen.nibiru.ui.api.view;

/**
 * Builds components (widgets, windows, etc) to be used in views. The purpose of
 * this interface is hiding UI framework specific implementations.
 */
public interface ViewFactory {
	/**
	 * Builds a main window.
	 * 
	 * @return The main window.
	 */
	MainWindow buildMainWindow();

	/**
	 * Builds a window.
	 * 
	 * @param style
	 *            The window style or null if not specified
	 * @return The window
	 */
	Window buildWindow(Window.Style style);

	/**
	 * Builds a label.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the label. Typically String.
	 * @param type
	 *            The class of data to be shown by the label. Typically String.
	 * @return The label
	 */
	<T> Label<T> buildLabel(Class<T> type);

	/**
	 * Builds a button.
	 * 
	 * @return The button.
	 */
	Button buildButton();

	/**
	 * Builds a text field.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the text field. Typically
	 *            String.
	 * @param type
	 *            The class of data to be shown by the text field. Typically
	 *            String.
	 * @return The text field
	 */
	<T> TextField<T> buildTextField(Class<T> type);

	/**
	 * Builds a password field.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the password field. Typically
	 *            String.
	 * @param type
	 *            The class of data to be shown by the password field. Typically
	 *            String.
	 * @return The password field
	 */
	<T> PasswordField<T> buildPasswordField(Class<T> type);

	/**
	 * Builds a multiline text area.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the password field. Typically
	 *            String.
	 * @param type
	 *            The class of data to be shown by the password field. Typically
	 *            String.
	 * @return The text area
	 */
	<T> TextArea<T> buildTextArea(Class<T> type);

	/**
	 * Builds a date field.
	 * 
	 * @return The date field
	 */
	DateField buildDateField();

	/**
	 * Builds a time field.
	 * 
	 * @return The time field
	 */
	TimeField buildTimeField();

	/**
	 * Builds a check box.
	 * 
	 * @return The check box
	 */
	CheckBox buildCheckBox();

	/**
	 * Builds a combo box.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the combo.
	 * @param type
	 *            The class of data to be shown by the combo.
	 * @return The combo box
	 */
	<T> ComboBox<T> buildComboBox(Class<T> type);

	/**
	 * Builds a list select.
	 * 
	 * @param <T>
	 *            The type of data to be shown by the list select.
	 * @param type
	 *            The class of data to be shown by the list select.
	 * @return The list select
	 */
	<T> ListSelect<T> buildListSelect(Class<T> type);

	/**
	 * Builds a table.
	 * 
	 * @return The table
	 */
	Table buildTable();

	/**
	 * Builds a panel with vertical layout.
	 * 
	 * @return The panel.
	 */
	Panel buildVerticalPanel();

	/**
	 * Builds a panel with horizontal layout.
	 * 
	 * @return The panel.
	 */
	Panel buildHorizontalPanel();

	/**
	 * Builds a panel with form layout.
	 * 
	 * @return The panel.
	 */
	FormPanel buildFormPanel();

	/**
	 * Builds a tabbed panel.
	 * 
	 * @return The panel
	 */
	Panel buildTabPanel();

	/**
	 * Builds an embedded.
	 * 
	 * @return The embedded
	 */
	Embedded buildEmbedded();

	/**
	 * Builds a contextual menu.
	 * 
	 * @return The context menu.
	 */
	ContextMenu buildContextMenu();
}
