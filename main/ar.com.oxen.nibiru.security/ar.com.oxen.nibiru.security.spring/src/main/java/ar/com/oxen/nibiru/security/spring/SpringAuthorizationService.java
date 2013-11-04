package ar.com.oxen.nibiru.security.spring;

import static ar.com.oxen.nibiru.security.spring.SpringAuthenticationService.AUTHENTICATION_KEY;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.session.api.Session;

public class SpringAuthorizationService implements AuthorizationService {
	private SecurityManager securityManager;
	private Session session;

	@Override
	public boolean isCallerInRole(String role) {
		Authentication authentication = this.session.get(AUTHENTICATION_KEY);

		if (authentication != null) {
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isUserInRole(String username, String role) {
		// TODO: esto limita los mecanismos de autorizacion y a la vez estoy
		// usando dos mecanismos distintos en la misma clase!
		return this.hasRole(this.securityManager.getUserData(username), role);
	}

	private boolean hasRole(UserData userData, String role) {
		if (userData != null) {
			for (String currentRole : userData.getRoles()) {
				if (role.equals(currentRole)) {
					return true;
				}
			}
		}
		return false;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
