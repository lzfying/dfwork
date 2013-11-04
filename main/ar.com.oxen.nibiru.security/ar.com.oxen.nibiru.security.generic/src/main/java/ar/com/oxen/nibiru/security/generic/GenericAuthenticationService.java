package ar.com.oxen.nibiru.security.generic;

import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.BadCredentialsException;
import ar.com.oxen.nibiru.security.api.HashService;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.security.manager.api.UserNotFoundException;
import ar.com.oxen.nibiru.session.api.Session;

public class GenericAuthenticationService implements AuthenticationService {
	private Session session;
	final static String USER_DATA_KEY = "ar.com.oxen.nibiru.security.generic.userData";
	private SecurityManager securityManager;
	private Profile profile;
	private HashService hashService;

	@Override
	public void login(String username, String password)
			throws BadCredentialsException {
		try {
			UserData userData = this.securityManager.getUserData(username);

			String hashedPassword = password != null && !password.equals("") ? this.hashService
					.hash(password) : "";

			String storedPassword = userData != null
					&& userData.getPassword() != null ? userData.getPassword()
					: "";

			if (userData == null || !storedPassword.equals(hashedPassword)) {
				this.raiseLoginError();
			} else {
				this.session.put(USER_DATA_KEY, userData);

				this.profile.activate(userData.getUsername(),
						userData.getFirstName(), userData.getLastName());
			}

		} catch (UserNotFoundException e) {
			this.raiseLoginError();
		}
	}

	@Override
	public void logout() {
		this.session.remove(USER_DATA_KEY);
	}

	@Override
	public String getLoggedUserName() {
		UserData userData = this.session.get(USER_DATA_KEY);
		return userData.getUsername();
	}

	private void raiseLoginError() {
		this.profile.deactivate();
		throw new BadCredentialsException();
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setHashService(HashService hashService) {
		this.hashService = hashService;
	}
}
