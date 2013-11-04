package ar.com.oxen.nibiru.crud.ui.api.form;

import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.validation.api.Validator;

public interface CrudFormView extends View, HasCloseWidget {
	void setEntityName(String entityName);

	void addField(CrudField crudField, CrudEntity<?> crudEntity);

	void setFieldValue(String fieldName, Object value);

	Object getFieldValue(String fieldName);

	void addEntityAction(String label, boolean requiresConfirmation,
			ClickHandler clickHandler);

	void addValidator(String name, Validator<?> validator);

	void removeValidator(String name, Validator<?> validator);

	boolean isValid();
}
