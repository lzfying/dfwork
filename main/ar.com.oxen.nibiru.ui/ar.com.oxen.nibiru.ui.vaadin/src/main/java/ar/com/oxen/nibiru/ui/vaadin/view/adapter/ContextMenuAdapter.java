package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ClickEvent;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;

import com.vaadin.ui.Window;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;

public class ContextMenuAdapter implements
		ar.com.oxen.nibiru.ui.api.view.ContextMenu {
	private ContextMenu contextMenu;
	private static ThreadLocal<Integer> lastX = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> lastY = new ThreadLocal<Integer>();

	public static void setLastCoordinates(int x, int y) {
		lastX.set(x);
		lastY.set(y);
	}

	public ContextMenuAdapter(ContextMenu contextMenu, Window mainWindow) {
		super();
		this.contextMenu = contextMenu;
		mainWindow.addComponent(contextMenu);
	}

	@Override
	public void show() {
		this.contextMenu.show(lastX.get(), lastY.get());
	}

	@Override
	public MenuItem addMenuItem(String caption, int position) {
		/*
		 * Se ignora la posicion porque el componente no tiene forma de
		 * cambiarla
		 */
		ContextMenuItem contextMenuItem = this.contextMenu.addItem(caption);
		return new ContextMenuItemAdapter(contextMenuItem);
	}

	@Override
	public void removeMenuItem(MenuItem menuItem) {
		ContextMenuItemAdapter contextMenuItemToRemove = (ContextMenuItemAdapter) menuItem;
		this.contextMenu.removeItem(contextMenuItemToRemove
				.getContextMenuItem());
	}

	private class ContextMenuItemAdapter implements MenuItem {
		private ContextMenuItem contextMenuItem;

		public ContextMenuItemAdapter(ContextMenuItem contextMenuItem) {
			super();
			this.contextMenuItem = contextMenuItem;
		}

		@Override
		public void setClickHandler(final ClickHandler clickHandler) {
			contextMenu.addListener(new ContextMenu.ClickListener() {
				private static final long serialVersionUID = 4738246495084843675L;

				public void contextItemClick(ClickEvent event) {
					if (contextMenuItem == event.getClickedItem()) {
						clickHandler.onClick();
					}
				}
			});
		}

		@Override
		public MenuItem addMenuItem(String caption, int position) {
			ContextMenuItem childContextMenuItem = this.contextMenuItem
					.addItem(caption);
			return new ContextMenuItemAdapter(childContextMenuItem);
		}

		@Override
		public void removeMenuItem(MenuItem menuItem) {
			ContextMenuItemAdapter contextMenuItemToRemove = (ContextMenuItemAdapter) menuItem;
			this.contextMenuItem.removeItem(contextMenuItemToRemove
					.getContextMenuItem());
		}

		public ContextMenuItem getContextMenuItem() {
			return contextMenuItem;
		}
	}
}
