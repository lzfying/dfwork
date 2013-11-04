package ar.com.oxen.nibiru.security.spring;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.BadCredentialsException;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.session.api.Session;

public class SpringAuthenticationService implements AuthenticationService {
	private Session session;
	final static String AUTHENTICATION_KEY = "ar.com.oxen.nibiru.security.spring.authentication";
	private AuthenticationManager authenticationManager;
	private SecurityManager securityManager;
	private Profile profile;

	@Override
	public void login(String username, String password)
			throws BadCredentialsException {
		try {
			Authentication authentication = this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							username, password != null ? password : ""));
			this.session.put(AUTHENTICATION_KEY, authentication);

			UserData userData = this.securityManager.getUserData(username);
			this.profile.activate(userData.getUsername(),
					userData.getFirstName(), userData.getLastName());

		} catch (AuthenticationException e) {
			this.profile.deactivate();
			throw new BadCredentialsException();
		}
	}

	@Override
	public void logout() {
		this.session.remove(AUTHENTICATION_KEY);
	}

	@Override
	public String getLoggedUserName() {
		UsernamePasswordAuthenticationToken authentication = this.session
				.get(AUTHENTICATION_KEY);
		return authentication.getName();
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
