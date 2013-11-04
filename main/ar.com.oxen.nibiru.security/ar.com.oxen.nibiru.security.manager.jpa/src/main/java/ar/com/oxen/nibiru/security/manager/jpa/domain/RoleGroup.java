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
@Table(name="SecurityRoleGroup")
public class RoleGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	@Show(order = 10)
	@Widget(type = WidgetType.TEXT_FIELD, maxLength = 50)
	private String name;

	@ManyToMany
	@Show(order = 20, inList = false)
	@Widget(type = WidgetType.MULTISELECT)
	private Set<Role> roles;

	@ManyToMany
	@Show(order = 30, inList = false)
	@Widget(type = WidgetType.MULTISELECT)
	private Set<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		RoleGroup other = (RoleGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
