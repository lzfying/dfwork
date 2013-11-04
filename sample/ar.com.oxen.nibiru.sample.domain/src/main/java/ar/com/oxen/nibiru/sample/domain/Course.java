package ar.com.oxen.nibiru.sample.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Show(order = 0)
	@Widget(type = WidgetType.TEXT_FIELD, readonly = true)
	private Integer id;

	@Column
	@Show(order = 10)
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	private Subject subject;

	@Column
	@Show(order = 30)
	private Date startDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@Show(order = 40, inList = false)
	private Set<Student> students;

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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
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
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
