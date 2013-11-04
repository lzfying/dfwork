package ar.com.oxen.nibiru.crud.ui.api.list;

import java.util.List;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public interface CrudListView extends View, HasCloseWidget {
	void setEntityName(String entityName);

	void addGlobalAction(String label, boolean requiresConfirmation,
			ClickHandler clickHandler);

	void setEntitySelectedHandler(ClickHandler entitySelectedHandler);

	void showEntityActions(List<EntityActionDefinition> actionDefinitions);

	void clearTable();

	void addColumn(String name, Class<?> type, int width);

	void addRow(Object[] values);

	int getSelectedRow();

	HasCloseHandler getCloseHander();

	class EntityActionDefinition {
		private String label;
		private boolean requiresConfirmation;
		private ClickHandler clickHandler;

		public EntityActionDefinition(String label,
				boolean requiresConfirmation, ClickHandler clickHandler) {
			super();
			this.label = label;
			this.requiresConfirmation = requiresConfirmation;
			this.clickHandler = clickHandler;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public boolean isRequiresConfirmation() {
			return requiresConfirmation;
		}

		public void setRequiresConfirmation(boolean requiresConfirmation) {
			this.requiresConfirmation = requiresConfirmation;
		}

		public ClickHandler getClickHandler() {
			return clickHandler;
		}

		public void setClickHandler(ClickHandler clickHandler) {
			this.clickHandler = clickHandler;
		}
	}
}
