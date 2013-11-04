package ar.com.oxen.nibiru.application.api.main;

import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;

public interface MainView extends View {
	HasMenuItems getMainMenu();

	MenuItem addMenuItem(String name, HasMenuItems parent, int position);

	void setUserName(String userName);
}
