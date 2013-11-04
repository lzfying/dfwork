package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.TextField;

public class TextFieldAdapter<T> extends AbstractTextFieldAdapter<T, TextField>
		implements ar.com.oxen.nibiru.ui.api.view.TextField<T> {
	public TextFieldAdapter(TextField adapted) {
		super(adapted);
	}
}
