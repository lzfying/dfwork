package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.ComboBox;

public class ComboBoxAdapter<T> extends AbstractFieldAdapter<T, ComboBox>
		implements ar.com.oxen.nibiru.ui.api.view.ComboBox<T> {
	public ComboBoxAdapter(ComboBox adapted) {
		super(adapted);
	}

	@Override
	public void setValue(T value) {
		this.getAdapted().setValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue() {
		return (T) this.getAdapted().getValue();
	}

	@Override
	public void addItem(T item) {
		this.addItem(item, item.toString());
	}

	@Override
	public void addItem(T item, String caption) {
		this.getAdapted().addItem(item);
		this.getAdapted().setItemCaption(item, caption);
	}
}
