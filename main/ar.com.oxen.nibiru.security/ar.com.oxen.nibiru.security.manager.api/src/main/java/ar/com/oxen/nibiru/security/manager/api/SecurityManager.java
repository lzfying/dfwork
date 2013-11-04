package ar.com.oxen.nibiru.security.manager.api;

/**
 * Security manager.
 */
public interface SecurityManager {
	/**
	 * Changes user's passowrd.
	 * 
	 * @param username
	 *            The user name
	 * @param oldPassword
	 *            The previous password
	 * @param newPassword
	 *            The new password
	 * @throws UserNotFoundException
	 *             If no user with the given name is found
	 * @throws InvalidOldPassword
	 *             If previous password is not valid
	 */
	void changePassword(String username, String oldPassword, String newPassword)
			throws UserNotFoundException, InvalidOldPassword;

	/**
	 * Retrieves the user data.
	 * 
	 * @param username
	 *            The user name
	 * @return The user data
	 * @throws UserNotFoundException
	 *             If no user with the given name is found
	 */
	UserData getUserData(String username) throws UserNotFoundException;
}
