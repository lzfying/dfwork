package ar.com.oxen.nibiru.security.manager.jpa;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.manager.api.InvalidOldPassword;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.manager.api.UserData;
import ar.com.oxen.nibiru.security.manager.api.UserNotFoundException;
import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;
import ar.com.oxen.nibiru.security.manager.jpa.domain.User;

public class JpaSecurityManager implements SecurityManager {
	private EntityManager entityManager;

	@Override
	public void changePassword(String username, String oldPassword,
			String newPassword) {
		User user = this.findUserByUsername(username);
		String currentPassword = user.getPassword() != null ? user
				.getPassword() : "";
		String validationPassword = oldPassword != null ? oldPassword : "";

		if (!currentPassword.equals(validationPassword)) {
			throw new InvalidOldPassword();
		}

		user.setPassword(newPassword);

		this.entityManager.persist(user);
	}

	@Override
	public UserData getUserData(String username) throws UserNotFoundException {
		this.checkUsers();
		return new UserAdapter(this.findUserByUsername(username));
	}

	private User findUserByUsername(String username) {
		try {
			Query query = this.entityManager
					.createQuery("select u from User u where u.username = :usernameParam");
			query.setParameter("usernameParam", username);
			User user = (User) query.getSingleResult();

			// TODO: check this
			this.entityManager.refresh(user); // Damn Hibernate cache
			return user;
		} catch (NoResultException e) {
			throw new UserNotFoundException();
		}
	}

	private void checkUsers() {
		TypedQuery<Number> query = this.entityManager.createQuery(
				"select count(u) from User u", Number.class);

		Number count = query.getSingleResult();

		if (count.intValue() == 0) {
			User admin = new User();
			admin.setFirstName("Admin");
			admin.setLastName("Admin");
			admin.setUsername("admin");
			admin.setPassword("");
			admin.setRoles(new HashSet<Role>());
			admin.getRoles().add(
					this.findOrCreateRole(AuthorizationService.OPERATOR_ROLE));
			admin.getRoles()
					.add(this
							.findOrCreateRole(AuthorizationService.ADMINISTRATOR_ROLE));
			this.entityManager.persist(admin);
		}
	}

	private Role findOrCreateRole(String roleName) {
		TypedQuery<Role> query = this.entityManager.createQuery(
				"select r from Role r where r.name = :roleName", Role.class);

		query.setParameter("roleName", roleName);

		List<Role> roles = query.getResultList();
		Role role;

		if (roles.size() == 0) {
			role = new Role();
			role.setName(roleName);
			role.setDescription(roleName);
			this.entityManager.persist(role);
		} else {
			role = roles.get(0);
		}

		return role;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
