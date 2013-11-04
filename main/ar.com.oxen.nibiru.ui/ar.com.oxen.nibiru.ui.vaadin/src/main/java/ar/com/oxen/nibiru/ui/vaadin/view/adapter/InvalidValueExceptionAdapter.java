package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.validation.api.ValidationException;

import com.vaadin.data.Validator.InvalidValueException;

public class InvalidValueExceptionAdapter extends InvalidValueException {
	private static final long serialVersionUID = 1247883114027570958L;
	private ValidationException validationException;

	public InvalidValueExceptionAdapter(ValidationException validationException) {
		super("");
		this.validationException = validationException;
	}

	public ValidationException getValidationException() {
		return validationException;
	}
}
