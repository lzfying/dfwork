package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.api.mvp.CloseHandler;
import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public abstract class AbstractWindowAdapter extends AbstractAdapter<Window>
		implements ar.com.oxen.nibiru.ui.api.view.Window {

	public AbstractWindowAdapter(Window adapted) {
		super(adapted);
	}

	@Override
	public void setValue(String value) {
		this.getAdapted().setCaption(value);
	}

	@Override
	public String getValue() {
		return this.getAdapted().getCaption();
	}

	@Override
	public void close() {
		if (this.getAdapted().getParent() != null) {
			this.getAdapted().getParent().removeWindow(this.getAdapted());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addComponent(ar.com.oxen.nibiru.ui.api.view.Component component) {
		AbstractAdapter<Component> adaptedComponent = (AbstractAdapter<Component>) component;
		this.getAdapted().addComponent(adaptedComponent.getAdapted());
	}

	@Override
	public void setCloseHandler(final CloseHandler closeHandler) {
		this.getAdapted().addListener(new Window.CloseListener() {
			private static final long serialVersionUID = -3542053659624927793L;

			@Override
			public void windowClose(CloseEvent e) {
				closeHandler.onClose();
			}
		});
	}

	@Override
	public boolean isModal() {
		return this.getAdapted().isModal();
	}

	@Override
	public void setModal(boolean modal) {
		this.getAdapted().setModal(modal);
	}
}
