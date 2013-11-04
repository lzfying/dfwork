package ar.com.oxen.nibiru.ui.api.view;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;

public interface Table extends Component, HasMenuItems {
	void addColumn(String label, Class<?> type);

	void addColumn(String label, Class<?> type, int width);

	void addRow(Object... values);

	void removeAllRows();
	
	void setRowSelectionHandler(ClickHandler rowSelectionHandler);

	int getSelectedRow();
}
