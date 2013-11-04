package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;

public abstract class AbstractComponentAdapter<T extends Component> extends AbstractAdapter<T>
		implements ar.com.oxen.nibiru.ui.api.view.Component {
	public AbstractComponentAdapter(T adapted) {
		super(adapted);
	}

	@Override
	public int getHeight() {
		return (int) this.getAdapted().getHeight();
	}

	@Override
	public int getWidth() {
		return (int) this.getAdapted().getWidth();
	}

	@Override
	public void setHeight(int height) {
		this.getAdapted().setHeight(height, Sizeable.UNITS_PIXELS);
	}

	@Override
	public void setWidth(int width) {
		this.getAdapted().setWidth(width, Sizeable.UNITS_PIXELS);
	}

}
