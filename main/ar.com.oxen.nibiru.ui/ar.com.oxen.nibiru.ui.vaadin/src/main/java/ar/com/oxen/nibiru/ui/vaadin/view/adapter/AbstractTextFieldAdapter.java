package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.AbstractTextField;

public abstract class AbstractTextFieldAdapter<T, K extends AbstractTextField> extends AbstractFieldAdapter<T, K>
		implements ar.com.oxen.nibiru.ui.api.view.TextField<T> {
	public AbstractTextFieldAdapter(K adapted) {
		super(adapted);
		adapted.setNullRepresentation("");
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

	@Override
	public int getMaxLength() {
		return this.getAdapted().getMaxLength();
	}

	@Override
	public void setMaxLength(int maxLength) {
		this.getAdapted().setMaxLength(maxLength);
	}
}
