package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.util.LinkedList;
import java.util.List;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuItemAdapter extends AbstractAdapter<MenuItem> implements
		ar.com.oxen.nibiru.ui.api.view.MenuItem {
	private List<Integer> positions = new LinkedList<Integer>();

	public MenuItemAdapter(MenuItem adapted) {
		super(adapted);
	}

	@Override
	public void setClickHandler(final ClickHandler clickHandler) {
		this.getAdapted().setCommand(new MenuBar.Command() {
			private static final long serialVersionUID = 4542297914908217936L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				clickHandler.onClick();

			}
		});
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
					this.getAdapted().getChildren().get(listPosition));
		} else {
			menuItem = this.getAdapted().addItem(caption, null);
		}

		this.positions.add(listPosition, position);

		return new MenuItemAdapter(menuItem);
	}

	@Override
	public void removeMenuItem(ar.com.oxen.nibiru.ui.api.view.MenuItem menuItem) {
		MenuItemAdapter menuItemAdapter = (MenuItemAdapter) menuItem;
		this.positions.remove(this.getAdapted().getChildren().indexOf(
				menuItemAdapter.getAdapted()));
		this.getAdapted().removeChild(menuItemAdapter.getAdapted());
	}
}
