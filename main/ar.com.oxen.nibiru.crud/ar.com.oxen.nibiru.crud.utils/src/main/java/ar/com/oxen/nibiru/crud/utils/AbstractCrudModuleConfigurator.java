package ar.com.oxen.nibiru.crud.utils;

import java.util.LinkedList;
import java.util.List;

import ar.com.oxen.commons.eventbus.api.EventHandler;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.manager.api.EditCrudEntityEvent;
import ar.com.oxen.nibiru.crud.manager.api.ManageChildCrudEntitiesEvent;
import ar.com.oxen.nibiru.crud.manager.api.ManageCrudEntitiesEvent;
import ar.com.oxen.nibiru.crud.ui.api.CrudPresenterFactory;
import ar.com.oxen.nibiru.crud.ui.api.CrudViewFactory;
import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;

public abstract class AbstractCrudModuleConfigurator extends
		AbstractModuleConfigurator<CrudViewFactory, CrudPresenterFactory> {
	private List<EventHandler<?>> registeredHandlers = new LinkedList<EventHandler<?>>();

	/**
	 * Adds a CRUD menu
	 */
	protected <K> void addCrudMenu(String menuName, String parentMenuExtension,
			CrudManager<K> crudManager) {
		this.registerMenu(menuName, parentMenuExtension, crudManager);
	}

	/**
	 * Adds a CRUD menu with allowed roles
	 */
	protected <K> void addCrudMenu(String menuName, String parentMenuExtension,
			CrudManager<K> crudManager, String[] allowedRoles) {
		this.registerMenu(menuName, parentMenuExtension, crudManager,
				allowedRoles);
	}

	/**
	 * Adds a CRUD without a menu option
	 */
	protected <K> void addCrud(CrudManager<K> crudManager,
			CrudActionExtension<K> crudActionExtension) {
		this.registerManageEntityEvent(crudManager);
		this.registerActions(crudManager, crudActionExtension);
		this.registerEditEntityEvent(crudManager);
	}

	/**
	 * Adds a CRUD with a menu option
	 */
	protected <K> void addCrudWithMenu(String menuName,
			String parentMenuExtension, CrudManager<K> crudManager,
			CrudActionExtension<K> crudActionExtension) {
		this.addCrudWithMenu(menuName, parentMenuExtension, crudManager,
				crudActionExtension, null);
	}

	/**
	 * Adds a CRUD with a menu option and allowed roles
	 */
	protected <K> void addCrudWithMenu(String menuName,
			String parentMenuExtension, CrudManager<K> crudManager,
			CrudActionExtension<K> crudActionExtension, String[] allowedRoles) {
		this.addCrudMenu(menuName, parentMenuExtension, crudManager,
				allowedRoles);
		this.addCrud(crudManager, crudActionExtension);
	}

	/**
	 * Adds a child menu CRUD menu option
	 */
	protected <T> void addChildCrudMenu(String menuName,
			CrudManager<?> parentCrudManager, String parentField,
			CrudManager<T> childCrudManager) {

		this.addChildCrudMenu(menuName, parentCrudManager, parentField,
				childCrudManager, null);
	}

	/**
	 * Adds a child menu CRUD menu option and allowed roles
	 */
	protected <T> void addChildCrudMenu(String menuName,
			CrudManager<?> parentCrudManager, String parentField,
			CrudManager<T> childCrudManager, String[] allowedRoles) {

		this.registerManageChildrenAction(menuName, parentCrudManager,
				childCrudManager, parentField, allowedRoles);
	}

	/**
	 * Adds a child menu CRUD without a menu option
	 */
	protected <T> void addChildCrud(CrudManager<?> parentCrudManager,
			CrudManager<T> childCrudManager,
			CrudActionExtension<T> childCrudActionExtension) {

		this.registerActions(childCrudManager, childCrudActionExtension);
		this.registerManageChildEntitiesEvent(parentCrudManager,
				childCrudManager);
		this.registerEditEntityEvent(childCrudManager);
	}

	/**
	 * Adds a child menu CRUD with a menu option
	 */
	protected <T> void addChildCrudWithMenu(String menuName,
			CrudManager<?> parentCrudManager, String parentField,
			CrudManager<T> childCrudManager,
			CrudActionExtension<T> childCrudActionExtension) {

		this.addChildCrudMenu(menuName, parentCrudManager, parentField,
				childCrudManager);
		this.addChildCrud(parentCrudManager, childCrudManager,
				childCrudActionExtension);
	}

	/**
	 * Adds a child menu CRUD with a menu option and allowed roles
	 */
	protected <T> void addChildCrudWithMenu(String menuName,
			CrudManager<?> parentCrudManager, String parentField,
			CrudManager<T> childCrudManager,
			CrudActionExtension<T> childCrudActionExtension,
			String[] allowedRoles) {

		this.addChildCrudMenu(menuName, parentCrudManager, parentField,
				childCrudManager, allowedRoles);
		this.addChildCrud(parentCrudManager, childCrudManager,
				childCrudActionExtension);
	}

	@Override
	public void shutdown() {
		super.shutdown();
		for (EventHandler<?> handler : this.registeredHandlers) {
			this.getEventBus().removeHandler(handler);
		}
	}

	protected void registerMenu(String menuName, String parentMenuExtension,
			CrudManager<?> crudManager) {
		this.registerMenu(menuName, parentMenuExtension, crudManager, null);
	}

	protected void registerMenu(String menuName, String parentMenuExtension,
			CrudManager<?> crudManager, String[] allowedRoles) {
		this.registerMenu(menuName, parentMenuExtension,
				ManageCrudEntitiesEvent.class, crudManager.getEntityTypeName(),
				allowedRoles);
	}

	protected <K> void registerActions(CrudManager<K> crudManager,
			CrudActionExtension<K> crudActionExtension) {
		this.registerExtension(crudActionExtension,
				crudManager.getEntityTypeName(), CrudActionExtension.class);
	}

	protected <K> void registerManageChildrenAction(String menuName,
			CrudManager<?> parentCrudManager,
			final CrudManager<?> childCrudManager, String parentField) {
		this.registerManageChildrenAction(menuName, parentCrudManager,
				childCrudManager, parentField, null);
	}

	protected <K> void registerManageChildrenAction(String menuName,
			CrudManager<?> parentCrudManager,
			final CrudManager<?> childCrudManager, String parentField,
			String[] allowedRoles) {
		this.registerExtension(new ManageChildrenCrudActionExtension<Object>(
				menuName, parentField, childCrudManager.getEntityTypeName(),
				this.getEventBus(), allowedRoles), parentCrudManager
				.getEntityTypeName(), CrudActionExtension.class);
	}

	protected void registerManageEntityEvent(final CrudManager<?> crudManager) {
		this.addEventHandler(ManageCrudEntitiesEvent.class,
				new EventHandler<ManageCrudEntitiesEvent>() {

					@Override
					public void onEvent(ManageCrudEntitiesEvent event) {
						activate(
								getViewFactory().buildListView(),
								getPresenterFactory().buildListPresenter(
										crudManager));
					}
				}, crudManager.getEntityTypeName());
	}

	protected <X> void registerEditEntityEvent(final CrudManager<X> crudManager) {
		Class<EditCrudEntityEvent<X>> eventClass = getEditCrudEntityEventClass();
		this.addEventHandler(eventClass,
				new EventHandler<EditCrudEntityEvent<X>>() {

					@Override
					public void onEvent(EditCrudEntityEvent<X> event) {
						activate(
								getViewFactory().buildFormView(),
								getPresenterFactory().buildFormPresenter(
										crudManager, event));
					}
				}, crudManager.getEntityTypeName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <X> Class<EditCrudEntityEvent<X>> getEditCrudEntityEventClass() {
		return (Class) EditCrudEntityEvent.class;
	}

	protected void registerManageChildEntitiesEvent(
			CrudManager<?> parentCrudManager,
			final CrudManager<?> childCrudManager) {
		this.addEventHandler(ManageChildCrudEntitiesEvent.class,
				new EventHandler<ManageChildCrudEntitiesEvent>() {

					@Override
					public void onEvent(ManageChildCrudEntitiesEvent event) {
						activate(
								getViewFactory().buildListView(),
								getPresenterFactory().buildListPresenter(
										childCrudManager,
										event.getParentField(),
										event.getParentEntity()));
					}
				}, childCrudManager.getEntityTypeName());
	}

	private <T> void addEventHandler(Class<T> eventClass,
			EventHandler<T> handler, String topic) {
		this.registeredHandlers.add(handler);
		this.getEventBus().addHandler(eventClass, handler, topic);
	}
}
