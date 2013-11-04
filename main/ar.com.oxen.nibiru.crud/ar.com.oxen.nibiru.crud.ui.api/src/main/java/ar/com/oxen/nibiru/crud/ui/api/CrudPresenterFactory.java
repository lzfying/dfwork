package ar.com.oxen.nibiru.crud.ui.api;

import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.manager.api.EditCrudEntityEvent;
import ar.com.oxen.nibiru.crud.ui.api.form.CrudFormView;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

/**
 * CRUD presenter factory.
 */
public interface CrudPresenterFactory {
	/**
	 * Builds a presenter for CRUD list.
	 * 
	 * @param crudManager
	 *            The CRUD manager
	 * @return The presenter
	 */
	<T> Presenter<CrudListView> buildListPresenter(CrudManager<T> crudManager);

	/**
	 * Builds a presenter for CRUD list which is filtered by a parent value.
	 * 
	 * @param crudManager
	 *            The CRUD manager
	 * @param parentField
	 *            The field used in order to filter the parent value.
	 * @param parentValue
	 *            The parent value.
	 * @return The presenter
	 */
	<T> Presenter<CrudListView> buildListPresenter(CrudManager<T> crudManager,
			String parentField, Object parentValue);

	/**
	 * Builds a presenter for CRUD form.
	 * 
	 * @param crudManager
	 *            The CRUD manager
	 * @return The presenter
	 */
	<T> Presenter<CrudFormView> buildFormPresenter(CrudManager<T> crudManager,
			EditCrudEntityEvent<T> event);
}
