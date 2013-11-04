package ar.com.oxen.nibiru.ui.api.view;

public interface ListSelect<T> extends FormField<T> {
	void addItem(T item);

	void addItem(T item, String caption);

	void setMultiSelect(boolean multiSelect);

	boolean isMultiSelect();
}
