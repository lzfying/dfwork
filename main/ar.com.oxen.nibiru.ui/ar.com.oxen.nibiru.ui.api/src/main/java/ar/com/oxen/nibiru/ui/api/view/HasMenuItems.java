package ar.com.oxen.nibiru.ui.api.view;

public interface HasMenuItems {
	MenuItem addMenuItem(String caption, int position);

	void removeMenuItem(MenuItem menuItem);
}
