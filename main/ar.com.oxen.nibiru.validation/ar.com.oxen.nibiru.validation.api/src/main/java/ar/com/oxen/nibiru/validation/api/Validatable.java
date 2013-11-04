package ar.com.oxen.nibiru.validation.api;

/**
 * Something that can be validated.
 * 
 * @param <T>
 *            Validated object data type.
 */
public interface Validatable<T> {
	/**
	 * Adss a validator.
	 * 
	 * @param validator
	 *            The validator
	 */
	void addValidator(Validator<T> validator);

	/**
	 * Removes a validator.
	 * 
	 * @param validator
	 *            The validator
	 */
	void removeValidator(Validator<T> validator);

	/**
	 * Validates the validatable data.
	 * 
	 * @throws ValidationException
	 *             If the validation is not met.
	 */
	void validate() throws ValidationException;
}
