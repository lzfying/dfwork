package ar.com.oxen.nibiru.security.spring;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.oxen.nibiru.security.api.HashService;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.security.manager.api.UserNotFoundException;

public class SecurityManagerUserDetailsService implements UserDetailsService {
	private SecurityManager securityManager;
	private HashService hashService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			UserData userData = this.securityManager.getUserData(username);
			
			Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

			for (String role : userData.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			String password = userData.getPassword();

			if (password == null || password.equals("")) {
				password = this.hashService.hash("");
			}

			return new User(username, password, authorities);
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public void setHashService(HashService hashService) {
		this.hashService = hashService;
	}
}
