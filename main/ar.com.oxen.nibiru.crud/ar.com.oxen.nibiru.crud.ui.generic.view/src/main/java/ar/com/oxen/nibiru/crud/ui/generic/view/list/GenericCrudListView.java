package ar.com.oxen.nibiru.crud.ui.generic.view.list;

import java.util.List;

import ar.com.oxen.nibiru.crud.ui.api.CrudViewFactory;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView;
import ar.com.oxen.nibiru.crud.ui.generic.view.AbstractCrudView;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.view.ContextMenu;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;
import ar.com.oxen.nibiru.ui.api.view.Panel;
import ar.com.oxen.nibiru.ui.api.view.Table;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;

public class GenericCrudListView extends AbstractCrudView implements
		CrudListView {
	private Table table;
	private Panel globalActionsPanel;

	public GenericCrudListView(ViewFactory viewFactory,
			MessageSource messageSource, LocaleHolder localeHolder) {
		super(viewFactory.buildWindow(Window.Style.LIST), viewFactory,
				messageSource, localeHolder);

		this.table = this.getViewFactory().buildTable();
		this.getAdapted().addComponent(table);

		Panel buttonsPanel = viewFactory.buildHorizontalPanel();
		this.getAdapted().addComponent(buttonsPanel);

		this.globalActionsPanel = viewFactory.buildHorizontalPanel();
		buttonsPanel.addComponent(this.globalActionsPanel);

		this.addClose(buttonsPanel);
	}

	@Override
	public void setEntityName(String entityName) {
		String translatedEntityName = this
				.getMessage(CrudViewFactory.I18N_ENTITY_PREFIX + entityName);
		this.getAdapted().setValue(
				this.getMessage("ar.com.oxen.nibiru.app.manage",
						translatedEntityName));
	}

	@Override
	public void addGlobalAction(String label, boolean requiresConfirmation,
			ClickHandler clickHandler) {
		this.globalActionsPanel.addComponent(createActionButton(label,
				requiresConfirmation, clickHandler));
	}

	@Override
	public void setEntitySelectedHandler(ClickHandler entitySelectedHandler) {
		this.table.setRowSelectionHandler(entitySelectedHandler);
	}

	@Override
	public void showEntityActions(List<EntityActionDefinition> actionDefinitions) {
		ContextMenu contextMenu = this.getViewFactory().buildContextMenu();

		for (EntityActionDefinition actionDefinition : actionDefinitions) {
			String action = this.i18nAction(actionDefinition.getLabel());

			MenuItem menu = contextMenu.addMenuItem(action, 999);
			menu.setClickHandler(this.addConfirmation(action,
					actionDefinition.isRequiresConfirmation(),
					actionDefinition.getClickHandler()));

		}
		contextMenu.show();
	}

	@Override
	public void clearTable() {
		this.table.removeAllRows();
	}

	@Override
	public void addColumn(String name, Class<?> type, int width) {
		String label = this
				.getMessage(CrudViewFactory.I18N_FIELD_PREFIX + name);

		if (width >= 0) {
			this.table.addColumn(label, type, width);
		} else {
			this.table.addColumn(label, type);
		}
	}

	@Override
	public void addRow(Object[] values) {
		this.table.addRow(values);
	}

	@Override
	public int getSelectedRow() {
		return this.table.getSelectedRow();
	}

	@Override
	public HasCloseHandler getCloseHander() {
		return this.getAdapted();
	}
}
