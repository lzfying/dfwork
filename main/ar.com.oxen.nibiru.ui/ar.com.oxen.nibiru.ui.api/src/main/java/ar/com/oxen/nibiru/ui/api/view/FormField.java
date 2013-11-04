package ar.com.oxen.nibiru.ui.api.view;

import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.HasValueChangeHandler;
import ar.com.oxen.nibiru.validation.api.Validatable;

public interface FormField<T> extends Component, HasValue<T>, HasValueChangeHandler, Validatable<T> {
	String getCaption();

	void setCaption(String caption);

	boolean isReadOnly();

	void setReadOnly(boolean readOnly);
	
	void setErrorMessage(String errorMessage);
}
