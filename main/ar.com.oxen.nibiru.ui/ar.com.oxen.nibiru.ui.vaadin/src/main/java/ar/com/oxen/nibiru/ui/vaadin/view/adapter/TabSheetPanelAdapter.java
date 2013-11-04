package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class TabSheetPanelAdapter extends AbstractComponentAdapter<TabSheet>
		implements ar.com.oxen.nibiru.ui.api.view.Panel {
	public TabSheetPanelAdapter(TabSheet adapted) {
		super(adapted);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addComponent(ar.com.oxen.nibiru.ui.api.view.Component component) {
		AbstractAdapter<Component> adaptedComponent = (AbstractAdapter<Component>) component;
		Component vaadinComponent = adaptedComponent.getAdapted();

		/* Workaround - remove the caption because Vaadin shows it twice */
		String caption = vaadinComponent.getCaption();
		vaadinComponent.setCaption(null);

		this.getAdapted().addTab(vaadinComponent, caption, null);
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
