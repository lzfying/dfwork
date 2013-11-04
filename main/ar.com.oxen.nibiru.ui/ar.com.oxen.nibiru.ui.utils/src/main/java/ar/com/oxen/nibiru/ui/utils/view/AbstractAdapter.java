package ar.com.oxen.nibiru.ui.utils.view;

public class AbstractAdapter<T> {
	private T adapted;

	public AbstractAdapter(T adapted) {
		super();
		this.adapted = adapted;
	}

	public T getAdapted() {
		return adapted;
	}
}
