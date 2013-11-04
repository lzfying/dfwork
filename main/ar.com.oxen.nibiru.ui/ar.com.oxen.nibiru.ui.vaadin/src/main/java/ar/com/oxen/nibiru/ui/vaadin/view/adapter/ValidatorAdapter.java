package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.validation.api.ValidationException;
import ar.com.oxen.nibiru.validation.api.Validator;

public class ValidatorAdapter<T> implements com.vaadin.data.Validator {
	private static final long serialVersionUID = 7337095689280366145L;
	private Validator<T> validator;

	public ValidatorAdapter(Validator<T> validator) {
		super();
		this.validator = validator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object value) throws InvalidValueException {
		try {
			this.validator.validate((T) value);
		} catch (ValidationException e) {
			throw new InvalidValueExceptionAdapter(e);
		}
	}

	@Override
	public boolean isValid(Object value) {
		try {
			this.validate(value);
			return true;
		} catch (InvalidValueException e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((validator == null) ? 0 : validator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValidatorAdapter<?> other = (ValidatorAdapter<?>) obj;
		if (validator == null) {
			if (other.validator != null) {
				return false;
			}
		} else if (!validator.equals(other.validator)) {
			return false;
		}
		return true;
	}
}
