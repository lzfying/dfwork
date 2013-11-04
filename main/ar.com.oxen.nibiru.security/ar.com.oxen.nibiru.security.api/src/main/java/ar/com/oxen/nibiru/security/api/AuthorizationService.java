package ar.com.oxen.nibiru.security.api;

/**
 * Service for authorizing actions and users.
 */
public interface AuthorizationService {
	/* Default roles */
	String OPERATOR_ROLE = "ar.com.oxen.nibiru.security.role.Operator";
	String ADMINISTRATOR_ROLE = "ar.com.oxen.nibiru.security.role.Administrator";

	/**
	 * Checks if the logged user has a given role.
	 * 
	 * @param role
	 *            The role name.
	 * @return True if the user has the role
	 */
	boolean isCallerInRole(String role);

	/**
	 * Checks if an specific user has a given role.
	 * 
	 * @param username
	 *            The username
	 * @param role
	 *            The role name.
	 * @return True if the user has the role
	 */
	boolean isUserInRole(String username, String role);
}
