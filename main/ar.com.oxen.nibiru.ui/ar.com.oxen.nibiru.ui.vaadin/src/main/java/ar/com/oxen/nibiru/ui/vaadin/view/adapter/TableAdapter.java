package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.ContextMenu;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Table;

public class TableAdapter extends AbstractComponentAdapter<Table> implements
		ar.com.oxen.nibiru.ui.api.view.Table {

	private int rowCount = 0;
	private int selectedRow = -1;
	private ar.com.oxen.nibiru.ui.api.view.ContextMenu menu;
	private int menuItemCount = 0;
	private ItemClickEvent.ItemClickListener nativeRowSelectionHandler;

	public TableAdapter(Table adapted, final ContextMenu menu) {
		super(adapted);
		this.menu = menu;

		adapted.addListener(new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = 31566034779814726L;

			@Override
			public void itemClick(ItemClickEvent event) {
				selectItem(event.getItemId());
				if (event.getButton() == ItemClickEvent.BUTTON_RIGHT
						&& menuItemCount > 0) {
					ContextMenuAdapter.setLastCoordinates(event.getClientX(),
							event.getClientY());
					menu.show();
				}
			}
		});
	}

	@Override
	public void addColumn(String label, Class<?> type) {
		this.getAdapted().addContainerProperty(label, type, null);
	}

	@Override
	public void addColumn(String label, Class<?> type, int width) {
		this.addColumn(label, type);
		this.getAdapted().setColumnWidth(label, width);
	}

	@Override
	public void addRow(Object... values) {
		this.getAdapted().addItem(values, rowCount++);
	}

	@Override
	public void removeAllRows() {
		this.getAdapted().removeAllItems();
		this.rowCount = 0;
	}

	@Override
	public void setRowSelectionHandler(final ClickHandler rowSelectionHandler) {
		if (this.nativeRowSelectionHandler != null) {
			this.getAdapted().removeListener(this.nativeRowSelectionHandler);
		}

		this.nativeRowSelectionHandler = new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.getButton() == ItemClickEvent.BUTTON_RIGHT) {
					ContextMenuAdapter.setLastCoordinates(event.getClientX(),
							event.getClientY());
					selectItem(event.getItemId());
					rowSelectionHandler.onClick();
				}
			}
		};

		this.getAdapted().addListener(this.nativeRowSelectionHandler);
	}

	@Override
	public int getSelectedRow() {
		return this.selectedRow;
	}

	@Override
	public MenuItem addMenuItem(String caption, int position) {
		this.menuItemCount++;
		return this.menu.addMenuItem(caption, position);
	}

	@Override
	public void removeMenuItem(MenuItem menuItem) {
		this.menuItemCount--;
		this.menu.removeMenuItem(menuItem);
	}

	private void selectItem(Object itemId) {
		this.selectedRow = ((Number) itemId).intValue();
		getAdapted().select(this.selectedRow);
	}
}
