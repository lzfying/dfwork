package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.api.view.ComponentContainer;
import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;

import com.vaadin.Application;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MainWindowAdapter extends AbstractWindowAdapter implements
		ar.com.oxen.nibiru.ui.api.view.MainWindow {
	private HasMenuItems mainMenu;
	private Application vaadinApplication;
	private Layout menuContainer;
	private Layout infoContainer;

	public MainWindowAdapter(Window adapted, Application vaadinApplication) {
		super(adapted);
		this.vaadinApplication = vaadinApplication;

		Layout upperContainer = new HorizontalLayout();
		upperContainer.setWidth("100%");
		getAdapted().addComponent(upperContainer);

		this.menuContainer = new HorizontalLayout();
		upperContainer.addComponent(this.menuContainer);

		this.infoContainer = new VerticalLayout();
		this.infoContainer.setWidth("100%");
		upperContainer.addComponent(this.infoContainer);
	}

	private HasMenuItems getMainMenu() {
		if (this.mainMenu == null) {
			synchronized (this) {
				if (this.mainMenu == null) {
					MenuBar menuBar = new MenuBar();
					this.menuContainer.addComponent(menuBar);
					this.mainMenu = new MenuBarAdapter(menuBar);
				}
			}
		}
		return mainMenu;
	}

	@Override
	public ar.com.oxen.nibiru.ui.api.view.MenuItem addMenuItem(String caption,
			int position) {
		return this.getMainMenu().addMenuItem(caption, position);
	}

	@Override
	public void removeMenuItem(ar.com.oxen.nibiru.ui.api.view.MenuItem menuItem) {
		this.mainMenu.removeMenuItem(menuItem);
	}

	@Override
	public void show() {
		Window currentMainWindow = this.vaadinApplication.getMainWindow();
		if (currentMainWindow != null) {
			this.vaadinApplication.removeWindow(currentMainWindow);
		}
		this.vaadinApplication.setMainWindow(this.getAdapted());
	}

	@Override
	public ComponentContainer getInfoContainer() {
		return new LayoutPanelAdapter(this.infoContainer);
	}
}
