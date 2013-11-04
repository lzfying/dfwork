package ar.com.oxen.nibiru.validation.api;

/**
 * Interface for classes that can perform validation.
 * 
 * @param <T>
 *            Validated object data type.
 */
public interface Validator<T> {
	/**
	 * Validates an object.
	 * 
	 * @param object
	 *            The object to be valdiated.
	 * @throws ValidationException
	 *             If the validation is not met.
	 */
	void validate(T object) throws ValidationException;
}
