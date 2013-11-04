package ar.com.oxen.nibiru.validation.generic;

import java.util.regex.Pattern;

import ar.com.oxen.nibiru.validation.api.ValidationException;
import ar.com.oxen.nibiru.validation.api.Validator;

/**
 * Regexp-based validator. Validated must be an String
 */
public class RegexpValidator implements Validator<String> {
	private Pattern pattern;
	private String errorCode;

	public RegexpValidator(String regexp, String errorCode) {
		super();
		this.pattern = Pattern.compile(regexp);
		this.errorCode = errorCode;
	}

	@Override
	public void validate(String object) throws ValidationException {
		if (!pattern.matcher(object).matches()) {
			throw new ValidationException(errorCode);
		}
	}
}
