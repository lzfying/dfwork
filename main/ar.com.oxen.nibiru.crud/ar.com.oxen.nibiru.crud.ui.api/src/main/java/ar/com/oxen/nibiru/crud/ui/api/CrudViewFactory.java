package ar.com.oxen.nibiru.crud.ui.api;

import ar.com.oxen.nibiru.crud.ui.api.form.CrudFormView;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView;

/**
 * CRUD presenter factory.
 */
public interface CrudViewFactory {
	String I18N_FIELD_PREFIX = "ar.com.oxen.nibiru.crud.field.";
	String I18N_ACTION_PREFIX = "ar.com.oxen.nibiru.crud.action.";
	String I18N_ENTITY_PREFIX = "ar.com.oxen.nibiru.crud.entity.";
	String I18N_TAB_PREFIX = "ar.com.oxen.nibiru.crud.tab.";
	String I18N_ERROR_PREFIX = "ar.com.oxen.nibiru.crud.error.";
	
	/**
	 * Builds the view for CRUD list.
	 * 
	 * @return The view
	 */
	CrudListView buildListView();

	/**
	 * Builds the view for CRUD form.
	 * 
	 * @return The view
	 */
	CrudFormView buildFormView();
}
