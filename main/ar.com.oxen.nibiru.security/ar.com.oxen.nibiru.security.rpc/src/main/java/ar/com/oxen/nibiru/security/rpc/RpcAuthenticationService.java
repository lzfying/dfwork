package ar.com.oxen.nibiru.security.rpc;

/**
 * Remote authentication service.
 * 
 */
public interface RpcAuthenticationService {
	/**
	 * Performs a login.
	 * 
	 * @param loginDto
	 *            A DTO with login request information
	 * @return A DTO with user information
	 */
	UserDto login(LoginDto loginDto);
}