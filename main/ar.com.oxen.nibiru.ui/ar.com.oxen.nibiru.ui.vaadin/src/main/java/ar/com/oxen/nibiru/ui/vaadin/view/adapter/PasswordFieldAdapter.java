package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.PasswordField;

public class PasswordFieldAdapter<T> extends
		AbstractTextFieldAdapter<T, PasswordField> implements
		ar.com.oxen.nibiru.ui.api.view.PasswordField<T> {
	public PasswordFieldAdapter(PasswordField adapted) {
		super(adapted);
	}
}
