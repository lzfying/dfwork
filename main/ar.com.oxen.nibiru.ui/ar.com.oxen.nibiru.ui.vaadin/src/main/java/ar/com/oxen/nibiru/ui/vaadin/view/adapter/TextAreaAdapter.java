package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.TextArea;

public class TextAreaAdapter<T> extends AbstractTextFieldAdapter<T, TextArea>
		implements ar.com.oxen.nibiru.ui.api.view.TextArea<T> {
	public TextAreaAdapter(TextArea adapted) {
		super(adapted);
	}
}
