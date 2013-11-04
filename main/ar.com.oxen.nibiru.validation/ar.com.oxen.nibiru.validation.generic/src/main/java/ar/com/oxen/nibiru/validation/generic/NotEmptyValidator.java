package ar.com.oxen.nibiru.validation.generic;

import ar.com.oxen.nibiru.validation.api.ValidationException;
import ar.com.oxen.nibiru.validation.api.Validator;

/**
 * Validatro for using on required fields.
 * The validated object:
 *  - Must not be null
 *  - It String representation must not be "" neither spaces.
 */
public class NotEmptyValidator implements Validator<Object> {
	private String errorCode = "required";

	public NotEmptyValidator() {
		super();
	}

	public NotEmptyValidator(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	@Override
	public void validate(Object object) throws ValidationException {
		if (object == null || object.toString().trim().equals("")) {
			throw new ValidationException(errorCode);
		}
	}
}
