package ar.com.oxen.nibiru.sample.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ar.com.oxen.nibiru.crud.bean.annotation.Action;
import ar.com.oxen.nibiru.crud.bean.annotation.Actions;
import ar.com.oxen.nibiru.crud.bean.annotation.Filter;
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
@Filter("authz.isCallerInRole('ar.com.oxen.nibiru.security.role.Administrator') ? null : \" o.name like 'T%' \"")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Show(order = 0)
	@Widget(type = WidgetType.TEXT_FIELD, readonly = true)
	private Integer id;

	@Column
	@Show(order = 10)
	private String name;

	@Column
	@Show(order = 15, inList = true)
	private Boolean active;

	@ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
	@Show(order = 30, inList = false)
	@Widget(type = WidgetType.MULTISELECT, tab = "courses")
	private Set<Course> courses;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		/*
		 * OpenJPA updates in cascade just one side on a bidirectional relation
		 * http://openjpa.apache.org/builds/1.0.1/apache-openjpa-1.0.1/docs/manual/jpa_overview_meta_field.html#jpa_overview_meta_mappedby
		 */
		if (this.courses == null) {
			this.courses = new HashSet<Course>();
		}
		for (Course course : this.courses) {
			if (courses == null || !courses.contains(course)) {
				course.getStudents().remove(this);
			}
		}
		if (courses != null) {
			for (Course course : courses) {
				course.getStudents().add(this);
			}
		}
		this.courses = courses;
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
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
