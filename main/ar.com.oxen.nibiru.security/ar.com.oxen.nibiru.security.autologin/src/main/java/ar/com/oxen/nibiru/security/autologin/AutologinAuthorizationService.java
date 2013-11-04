package ar.com.oxen.nibiru.security.autologin;

import ar.com.oxen.nibiru.security.api.AuthorizationService;

public class AutologinAuthorizationService implements AuthorizationService {
	@Override
	public boolean isCallerInRole(String role) {
		return true;
	}

	@Override
	public boolean isUserInRole(String username, String role) {
		return true;
	}
}
