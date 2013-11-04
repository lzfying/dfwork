package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.Label;

public class LabelAdapter<T> extends AbstractComponentAdapter<Label> implements
		ar.com.oxen.nibiru.ui.api.view.Label<T> {
	public LabelAdapter(Label adapted) {
		super(adapted);
	}

	@Override
	public void setValue(T value) {
		this.getAdapted().getPropertyDataSource().setValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue() {
		return (T) this.getAdapted().getPropertyDataSource().getValue();
	}
}
