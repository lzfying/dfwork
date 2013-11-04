package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.Window;

public class WindowAdapter extends AbstractWindowAdapter {
	private Window mainWindow;

	public WindowAdapter(Window adapted, Window mainWindow) {
		super(adapted);
		adapted.getContent().setSizeUndefined();
		adapted.center();
		this.mainWindow = mainWindow;
	}

	@Override
	public void show() {
		if (this.getAdapted().getParent() != null) {
			this.getAdapted().getParent().removeWindow(this.getAdapted());
		}
		this.mainWindow.addWindow(this.getAdapted());
	}
}
