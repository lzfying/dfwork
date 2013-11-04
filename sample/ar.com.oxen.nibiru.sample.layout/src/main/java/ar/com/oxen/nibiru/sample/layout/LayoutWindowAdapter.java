package ar.com.oxen.nibiru.sample.layout;

import ar.com.oxen.nibiru.ui.api.mvp.CloseHandler;
import ar.com.oxen.nibiru.ui.api.view.Component;
import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.ComponentContainer.ComponentDetachEvent;
import com.vaadin.ui.ComponentContainer.ComponentDetachListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class LayoutWindowAdapter extends AbstractAdapter<Layout> implements
		ar.com.oxen.nibiru.ui.api.view.Window {
	private TabSheet mainWindow;
	private CloseHandler closeHandler;
	private Window parentnWindow;

	public LayoutWindowAdapter(Layout adapted, Window mainWindow) {
		super(adapted);
		this.parentnWindow=mainWindow;
		this.mainWindow = (TabSheet) mainWindow.getData();
		getAdapted().addListener(new ComponentDetachListener() {
			private static final long serialVersionUID = -4704694231947001379L;

			@Override
			public void componentDetachedFromContainer(
					ComponentDetachEvent event) {
				if (closeHandler != null) {
					closeHandler.onClose();
				}
			}
		});
	}

	@Override
	public void show() {
		System.out.println(getAdapted().getCaption() + ":"
				+ mainWindow.getHeight());
		System.out.println("parentnWindow :"
				+ parentnWindow.getHeight());
		System.out.println("mainlayout :"
				+ parentnWindow.getContent().getHeight());
		if (mainWindow.getTab(getAdapted()) == null) {
			TabSheet.Tab tab = mainWindow.addTab(getAdapted());
			tab.setClosable(true);
		}
		mainWindow.setSelectedTab(getAdapted());
	}

	@Override
	public void close() {
		mainWindow.removeTab(mainWindow.getTab(getAdapted()));
	}

	@Override
	public void setValue(String value) {
		getAdapted().setCaption(value);
	}

	@Override
	public String getValue() {
		return getAdapted().getCaption();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addComponent(Component component) {
		AbstractAdapter<com.vaadin.ui.Component> adapter = (AbstractAdapter<com.vaadin.ui.Component>) component;
		getAdapted().addComponent(adapter.getAdapted());
	}

	@Override
	public void setCloseHandler(CloseHandler closeHandler) {
		this.closeHandler = closeHandler;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	public void setModal(boolean modal) {
	}
}
