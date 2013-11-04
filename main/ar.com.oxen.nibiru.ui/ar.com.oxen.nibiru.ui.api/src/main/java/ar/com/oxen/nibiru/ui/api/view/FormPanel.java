package ar.com.oxen.nibiru.ui.api.view;

public interface FormPanel extends Panel {
	boolean isValid();

	Iterable<FormField<?>> getFields();
}
