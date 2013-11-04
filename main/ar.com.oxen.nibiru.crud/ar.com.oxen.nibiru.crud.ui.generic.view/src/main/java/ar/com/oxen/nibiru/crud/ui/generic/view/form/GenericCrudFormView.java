package ar.com.oxen.nibiru.crud.ui.generic.view.form;

import java.util.HashMap;
import java.util.Map;

import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.crud.ui.api.CrudViewFactory;
import ar.com.oxen.nibiru.crud.ui.api.form.CrudFormView;
import ar.com.oxen.nibiru.crud.ui.generic.view.AbstractCrudView;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.ComboBox;
import ar.com.oxen.nibiru.ui.api.view.FormField;
import ar.com.oxen.nibiru.ui.api.view.FormPanel;
import ar.com.oxen.nibiru.ui.api.view.ListSelect;
import ar.com.oxen.nibiru.ui.api.view.Panel;
import ar.com.oxen.nibiru.ui.api.view.PasswordField;
import ar.com.oxen.nibiru.ui.api.view.TextArea;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.validation.api.ValidationException;
import ar.com.oxen.nibiru.validation.api.Validator;

public class GenericCrudFormView extends AbstractCrudView implements
		CrudFormView {
	private Panel fieldsTabPanel;
	private Map<String, FormPanel> fieldsPanels;
	private Panel actionsPanel;
	private Map<String, FormField<Object>> fieldToValue;

	public GenericCrudFormView(ViewFactory viewFactory,
			MessageSource messageSource, LocaleHolder localeHolder) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory, messageSource,
				localeHolder);

		this.fieldsTabPanel = viewFactory.buildTabPanel();
		this.fieldsTabPanel.setWidth(400);
		this.getAdapted().addComponent(fieldsTabPanel);

		this.fieldsPanels = new HashMap<String, FormPanel>();
		this.fieldToValue = new HashMap<String, FormField<Object>>();

		Panel buttonsPanel = viewFactory.buildHorizontalPanel();
		this.getAdapted().addComponent(buttonsPanel);

		actionsPanel = viewFactory.buildHorizontalPanel();
		buttonsPanel.addComponent(actionsPanel);

		this.addClose(buttonsPanel);
	}

	@Override
	public void setEntityName(String entityName) {
		String translatedEntityName = this
				.getMessage(CrudViewFactory.I18N_ENTITY_PREFIX + entityName);
		this.getAdapted().setValue(
				this.getMessage("ar.com.oxen.nibiru.app.edit",
						translatedEntityName));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addField(CrudField crudField, CrudEntity<?> crudEntity) {
		FormField<?> field;

		CrudField.FormInfo formInfo = crudField.getFormInfo();

		switch (formInfo.getWidgetType()) {
		case TEXT_FIELD:
			TextField<?> textField = this.getViewFactory().buildTextField(
					(Class<?>) crudField.getType());

			textField.setMaxLength(formInfo. getMaxLength());
			
			field = textField;
			break;

		case PASSWORD_FIELD:
			PasswordField<?> passowrdField = this.getViewFactory()
					.buildPasswordField((Class<?>) crudField.getType());
			passowrdField.setMaxLength(formInfo.getMaxLength());

			field = passowrdField;
			break;

		case TEXT_AREA:
			TextArea<?> textArea = this.getViewFactory().buildTextArea(
					(Class<?>) crudField.getType());
			textArea.setMaxLength(formInfo.getMaxLength());
			textArea.setWidth(400);
			textArea.setHeight(200);
			this.fieldsTabPanel.setWidth(600);

			field = textArea;
			break;

		case DATE_FIELD:
			field = (FormField<?>) this.getViewFactory().buildDateField();
			break;

		case TIME_FIELD:
			field = (FormField<?>) this.getViewFactory().buildTimeField();
			break;

		case CHECK_BOX:
			field = (FormField<?>) this.getViewFactory().buildCheckBox();
			break;

		case COMBO_BOX:
			ComboBox<?> combo = this.getViewFactory().buildComboBox(
					(Class<?>) crudField.getType());

			if (formInfo.getValues() != null && formInfo.getValues().length > 0) {
				for (String item : formInfo.getValues()) {
					((ComboBox<String>) combo).addItem(item);
				}
			} else {
				for (Object item : crudEntity.getAvailableValues(crudField)) {
					((ComboBox<Object>) combo).addItem(item);
				}				
			}

			field = combo;
			break;

		case MULTISELECT:
			ListSelect<?> select = this.getViewFactory().buildListSelect(
					(Class<?>) crudField.getType());
			select.setMultiSelect(true);

			for (Object item : crudEntity.getAvailableValues(crudField)) {
				((ListSelect<Object>) select).addItem(item);
			}

			field = select;
			break;

		default:
			throw new IllegalArgumentException("invalid widget type:"
					+ formInfo.getWidgetType());
		}

		field.setCaption(" " + this.getMessage(CrudViewFactory.I18N_FIELD_PREFIX
				+ crudField.getName()));
		field.setReadOnly(formInfo.isReadonly());

		if (formInfo.getMaxLength() > this.fieldsTabPanel.getWidth()) {
			this.fieldsTabPanel.setWidth((int) (formInfo.getMaxLength() * 1.2 ));
		}
		
		String tabName = crudField.getFormInfo().getTab();
		FormPanel fieldsPanel = this.fieldsPanels.get(tabName);

		if (fieldsPanel == null) {
			fieldsPanel = this.getViewFactory().buildFormPanel();
			fieldsPanel.setValue(this
					.getMessage(CrudViewFactory.I18N_TAB_PREFIX + tabName));
			this.fieldsTabPanel.addComponent(fieldsPanel);
			this.fieldsPanels.put(tabName, fieldsPanel);
		}

		fieldsPanel.addComponent(field);
		this.fieldToValue.put(crudField.getName(), (FormField<Object>) field);
	}

	@Override
	public void setFieldValue(String fieldName, Object value) {
		this.fieldToValue.get(fieldName).setValue(value);
	}

	@Override
	public Object getFieldValue(String fieldName) {
		return this.fieldToValue.get(fieldName).getValue();
	}

	@Override
	public void addEntityAction(String label, boolean requiresConfirmation,
			ClickHandler clickHandler) {
		this.actionsPanel.addComponent(this.createActionButton(label,
				requiresConfirmation, clickHandler));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addValidator(String name, Validator<?> validator) {
		this.fieldToValue.get(name).addValidator((Validator<Object>) validator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeValidator(String name, Validator<?> validator) {
		this.fieldToValue.get(name).addValidator((Validator<Object>) validator);
	}

	@Override
	public boolean isValid() {
		for (FormPanel form : this.fieldsPanels.values()) {
			if (!form.isValid()) {
				this.showErrors();
				return false;
			}
		}
		return true;
	}

	private void showErrors() {
		for (FormPanel form : this.fieldsPanels.values()) {
			for (FormField<?> field : form.getFields()) {
				try {
					field.validate();
				} catch (ValidationException e) {
					this.setFieldErrors(field, e.getErrorCodes());
				}
			}
		}
	}

	private void setFieldErrors(FormField<?> field, Iterable<String> errorCodes) {
		StringBuilder sb = new StringBuilder();

		for (String errorCode : errorCodes) {
			sb.append(this.getMessage(CrudViewFactory.I18N_ERROR_PREFIX
					+ errorCode));
			sb.append("\n");
		}

		field.setErrorMessage(sb.toString());
	}
}
