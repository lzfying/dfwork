package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.ListSelect;

public class ListSelectAdapter<T> extends AbstractFieldAdapter<T, ListSelect>
		implements ar.com.oxen.nibiru.ui.api.view.ListSelect<T> {
	public ListSelectAdapter(ListSelect adapted) {
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

	@Override
	public boolean isMultiSelect() {
		return this.getAdapted().isMultiSelect();
	}

	@Override
	public void setMultiSelect(boolean multiSelect) {
		this.getAdapted().setMultiSelect(multiSelect);
	}
}
