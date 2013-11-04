package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;

public class LayoutPanelAdapter extends AbstractComponentAdapter<Layout>
		implements ar.com.oxen.nibiru.ui.api.view.Panel {
	public LayoutPanelAdapter(Layout adapted) {
		super(adapted);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addComponent(ar.com.oxen.nibiru.ui.api.view.Component component) {
		AbstractAdapter<Component> adaptedComponent = (AbstractAdapter<Component>) component;
		this.getAdapted().addComponent(adaptedComponent.getAdapted());
	}

	@Override
	public String getValue() {
		return this.getAdapted().getCaption();
	}

	@Override
	public void setValue(String value) {
		this.getAdapted().setCaption(value);
	}
}
