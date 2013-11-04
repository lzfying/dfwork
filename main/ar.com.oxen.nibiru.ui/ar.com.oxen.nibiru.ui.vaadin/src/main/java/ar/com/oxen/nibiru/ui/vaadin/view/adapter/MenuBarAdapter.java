package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.util.LinkedList;
import java.util.List;

import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;
import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuBarAdapter extends AbstractAdapter<MenuBar> implements
		HasMenuItems {
	private List<Integer> positions = new LinkedList<Integer>();

	public MenuBarAdapter(MenuBar adapted) {
		super(adapted);
	}

	@Override
	public ar.com.oxen.nibiru.ui.api.view.MenuItem addMenuItem(String caption,
			int position) {
		int listPosition = 0;
		while (listPosition < positions.size()
				&& positions.get(listPosition) < position) {
			listPosition++;
		}

		MenuItem menuItem;
		if (listPosition < positions.size()) {
			menuItem = this.getAdapted().addItemBefore(caption, null, null,
					this.getAdapted().getItems().get(listPosition));
		} else {
			menuItem = this.getAdapted().addItem(caption, null);
		}

		this.positions.add(listPosition, position);

		return new MenuItemAdapter(menuItem);
	}

	@Override
	public void removeMenuItem(ar.com.oxen.nibiru.ui.api.view.MenuItem menuItem) {
		MenuItemAdapter menuItemAdapter = (MenuItemAdapter) menuItem;
		this.positions.remove(this.getAdapted().getItems()
				.indexOf(menuItemAdapter.getAdapted()));
		this.getAdapted().removeItem(menuItemAdapter.getAdapted());
	}
}
