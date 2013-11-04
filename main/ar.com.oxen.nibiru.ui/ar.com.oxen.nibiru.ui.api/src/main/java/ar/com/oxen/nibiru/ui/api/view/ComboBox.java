package ar.com.oxen.nibiru.ui.api.view;

public interface ComboBox<T> extends FormField<T> {
	void addItem(T item);

	void addItem(T item, String caption);
}
