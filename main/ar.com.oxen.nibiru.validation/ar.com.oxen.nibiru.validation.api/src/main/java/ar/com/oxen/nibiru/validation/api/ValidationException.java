package ar.com.oxen.nibiru.validation.api;

import java.util.Arrays;
import java.util.List;

/**
 * Exception that holds the error codes. Such error codes can be prefixed (for
 * example, at UI level) in order to get an internationalized description.
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 4252348877291968679L;
	private List<String> errorCodes;

	public ValidationException(String... errorCodes) {
		super();
		this.errorCodes = Arrays.asList(errorCodes);
	}

	/**
	 * @return The error codes.
	 */
	public Iterable<String> getErrorCodes() {
		return errorCodes;
	}
}
