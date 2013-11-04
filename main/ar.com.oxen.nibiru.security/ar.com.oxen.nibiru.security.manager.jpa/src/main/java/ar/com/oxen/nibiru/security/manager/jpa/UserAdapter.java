package ar.com.oxen.nibiru.security.manager.jpa;

import java.util.HashSet;
import java.util.Set;

import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;
import ar.com.oxen.nibiru.security.manager.jpa.domain.RoleGroup;
import ar.com.oxen.nibiru.security.manager.jpa.domain.User;

class UserAdapter implements UserData {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Set<String> roles;

	public UserAdapter(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.roles = new HashSet<String>();
		for (Role role : user.getRoles()) {
			roles.add(role.getName());
		}
		for (RoleGroup group : user.getGroups()) {
			for (Role role : group.getRoles()) {
				roles.add(role.getName());
			}
		}
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public Iterable<String> getRoles() {
		return this.roles;
	}
}
