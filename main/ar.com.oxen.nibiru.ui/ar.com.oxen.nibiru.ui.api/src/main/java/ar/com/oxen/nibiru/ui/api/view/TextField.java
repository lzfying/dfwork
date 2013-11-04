package ar.com.oxen.nibiru.ui.api.view;

public interface TextField<T> extends FormField<T> {
	void setMaxLength(int maxLength);

	int getMaxLength();
}
