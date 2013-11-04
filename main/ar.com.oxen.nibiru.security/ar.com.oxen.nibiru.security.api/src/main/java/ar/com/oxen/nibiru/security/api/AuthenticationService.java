package ar.com.oxen.nibiru.security.api;

/**
 * Service for authenticating users.
 */
public interface AuthenticationService {
	/**
	 * Performs an user log-on.
	 * 
	 * @param username
	 *            The user name
	 * @param password
	 *            The password
	 * @throws BadCredentialsException
	 *             If the user name and/or the password is not valid
	 */
	void login(String username, String password) throws BadCredentialsException;

	/**
	 * Performs an user log-off.
	 */
	void logout();

	/**
	 * @return The login name of the logged user (if any).
	 */
	String getLoggedUserName();
}
