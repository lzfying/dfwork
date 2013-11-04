package ar.com.oxen.nibiru.security.autologin;

import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.BadCredentialsException;

public class AutologinAuthenticationService implements AuthenticationService {

	@Override
	public void login(String user, String password)
			throws BadCredentialsException {
	}

	@Override
	public void logout() {
	}

	@Override
	public String getLoggedUserName() {
		return AutologinProfile.USERNAME;
	}
}
