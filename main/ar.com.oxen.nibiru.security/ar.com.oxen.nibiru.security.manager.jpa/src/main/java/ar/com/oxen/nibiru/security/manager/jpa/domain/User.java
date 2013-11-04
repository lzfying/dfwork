package ar.com.oxen.nibiru.security.manager.jpa.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.com.oxen.nibiru.crud.bean.annotation.Action;
import ar.com.oxen.nibiru.crud.bean.annotation.Actions;
import ar.com.oxen.nibiru.crud.bean.annotation.Show;
import ar.com.oxen.nibiru.crud.bean.annotation.Widget;
import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.WidgetType;

@Entity
@Actions({
		@Action(name = CrudAction.NEW, requiresEntity = false, showInForm = false),
		@Action(name = CrudAction.EDIT, requiresEntity = true, showInForm = false),
		@Action(name = CrudAction.UPDATE, requiresEntity = true, showInList = false),
		@Action(name = CrudAction.DELETE, requiresEntity = true, showInForm = false, requiresConfirmation = true) })
@Table(name = "SecurityUser")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	@Show(order = 10)
	@Widget(type = WidgetType.TEXT_FIELD, maxLength = 50)
	private String firstName;

	@Column
	@Show(order = 20)
	@Widget(type = WidgetType.TEXT_FIELD, maxLength = 50)
	private String lastName;

	@Column(unique = true)
	@Show(order = 30)
	@Widget(type = WidgetType.TEXT_FIELD, maxLength = 50)
	private String username;

	@Column
	@Show(order = 40, inList = false)
	@Widget(type = WidgetType.PASSWORD_FIELD, readonly = true, maxLength = 50)
	private String password;

	@ManyToMany
	@Show(order = 50, inList = false)
	@Widget(type = WidgetType.MULTISELECT)
	private Set<Role> roles;

	@ManyToMany(mappedBy = "users")
	@Show(order = 60, inList = false)
	@Widget(type = WidgetType.MULTISELECT)
	private Set<RoleGroup> groups;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<RoleGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<RoleGroup> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
