package ar.com.oxen.nibiru.ui.vaadin.view;

import java.util.Date;

import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.CheckBox;
import ar.com.oxen.nibiru.ui.api.view.ComboBox;
import ar.com.oxen.nibiru.ui.api.view.ContextMenu;
import ar.com.oxen.nibiru.ui.api.view.DateField;
import ar.com.oxen.nibiru.ui.api.view.Embedded;
import ar.com.oxen.nibiru.ui.api.view.FormPanel;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.ListSelect;
import ar.com.oxen.nibiru.ui.api.view.MainWindow;
import ar.com.oxen.nibiru.ui.api.view.Panel;
import ar.com.oxen.nibiru.ui.api.view.PasswordField;
import ar.com.oxen.nibiru.ui.api.view.Table;
import ar.com.oxen.nibiru.ui.api.view.TextArea;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.TimeField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.ButtonAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.CheckBoxAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.ComboBoxAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.ContextMenuAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.DateFieldAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.EmbeddedAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.FormPanelAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.LabelAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.LayoutPanelAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.ListSelectAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.MainWindowAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.PasswordFieldAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TabSheetPanelAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TableAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TextAreaAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TextFieldAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TimeFieldAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.WindowAdapter;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class VaadinViewFactory implements ViewFactory {
	private ApplicationAccessor applicationAccessor;

	@Override
	public MainWindow buildMainWindow() {
		return new MainWindowAdapter(new com.vaadin.ui.Window(),
				this.applicationAccessor.getApplication());
	}

	@Override
	public Window buildWindow(Window.Style style) {
		return new WindowAdapter(new com.vaadin.ui.Window(),
				this.applicationAccessor.getApplication().getMainWindow());
	}

	@Override
	public <T> Label<T> buildLabel(Class<T> type) {
		return new LabelAdapter<T>(new com.vaadin.ui.Label(
				new ObjectProperty<T>(null, type)));
	}

	@Override
	public Button buildButton() {
		return new ButtonAdapter(new com.vaadin.ui.Button());
	}

	@Override
	public <T> TextField<T> buildTextField(Class<T> type) {
		return new TextFieldAdapter<T>(new com.vaadin.ui.TextField(
				new ObjectProperty<T>(null, type)));
	}

	@Override
	public <T> TextArea<T> buildTextArea(Class<T> type) {
		return new TextAreaAdapter<T>(new com.vaadin.ui.TextArea(
				new ObjectProperty<T>(null, type)));
	}

	@Override
	public <T> PasswordField<T> buildPasswordField(Class<T> type) {
		return new PasswordFieldAdapter<T>(new com.vaadin.ui.PasswordField(
				new ObjectProperty<T>(null, type)));
	}

	@Override
	public DateField buildDateField() {
		return new DateFieldAdapter(new com.vaadin.ui.DateField(
				new ObjectProperty<Date>(null, Date.class)));
	}

	@Override
	public TimeField buildTimeField() {
		return new TimeFieldAdapter(new com.vaadin.ui.TextField(
				new ObjectProperty<String>(null, String.class)));
	}

	@Override
	public CheckBox buildCheckBox() {
		return new CheckBoxAdapter(new com.vaadin.ui.CheckBox(null,
				new ObjectProperty<Boolean>(Boolean.FALSE, Boolean.class)));
	}

	@Override
	public <T> ComboBox<T> buildComboBox(Class<T> type) {
		return new ComboBoxAdapter<T>(new com.vaadin.ui.ComboBox(null));
	}

	@Override
	public <T> ListSelect<T> buildListSelect(Class<T> type) {
		return new ListSelectAdapter<T>(new com.vaadin.ui.ListSelect(null));
	}

	@Override
	public Table buildTable() {
		return new TableAdapter(new com.vaadin.ui.Table(),
				this.buildContextMenu());
	}

	@Override
	public Panel buildVerticalPanel() {
		return new LayoutPanelAdapter(new VerticalLayout());
	}

	@Override
	public FormPanel buildFormPanel() {
		return new FormPanelAdapter(new Form());
	}

	@Override
	public Panel buildHorizontalPanel() {
		return new LayoutPanelAdapter(new HorizontalLayout());
	}

	@Override
	public Panel buildTabPanel() {
		return new TabSheetPanelAdapter(new TabSheet());
	}

	@Override
	public Embedded buildEmbedded() {
		return new EmbeddedAdapter(new com.vaadin.ui.Embedded(),
				this.applicationAccessor.getApplication());
	}

	public void setApplicationAccessor(ApplicationAccessor applicationAccessor) {
		this.applicationAccessor = applicationAccessor;
	}

	@Override
	public ContextMenu buildContextMenu() {
		return new ContextMenuAdapter(
				new org.vaadin.peter.contextmenu.ContextMenu(),
				this.applicationAccessor.getApplication().getMainWindow());
	}
}
