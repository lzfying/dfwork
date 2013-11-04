package ar.com.oxen.nibiru.ui.api.mvp;

public interface HasValue<T> {
	void setValue(T value);

	T getValue();
}
