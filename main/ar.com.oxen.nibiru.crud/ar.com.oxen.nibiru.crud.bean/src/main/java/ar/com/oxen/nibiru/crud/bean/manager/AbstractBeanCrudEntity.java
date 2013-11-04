package ar.com.oxen.nibiru.crud.bean.manager;

import java.util.List;

import ar.com.oxen.commons.bean.api.BeanWrapper;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;

public abstract class AbstractBeanCrudEntity<T> implements CrudEntity<T> {
	private BeanWrapper<T> bean;
	private String pkName;
	private List<CrudField> formFields;

	public AbstractBeanCrudEntity(BeanWrapper<T> bean, String pkName,
			List<CrudField> formFields) {
		super();
		this.bean = bean;
		this.pkName = pkName;
		this.formFields = formFields;
	}

	@Override
	public List<CrudField> getFormFields() {
		return this.formFields;
	}

	@Override
	public Object getId() {
		return this.getValue(this.pkName);
	}

	@Override
	public Object getValue(CrudField field) {
		return this.getValue(field.getName());
	}

	@Override
	public void setValue(CrudField field, Object value) {
		this.setValue(field.getName(), value);
	}

	@Override
	public Object getValue(String fieldName) {
		return this.bean.getProperty(fieldName);
	}

	@Override
	public void setValue(String fieldName, Object value) {
		this.bean.setProperty(fieldName, value);
	}

	@Override
	public T getEntity() {
		return this.bean.getBean();
	}

	@Override
	public String getEntityTypeName() {
		return this.bean.getBean().getClass().getName();
	}

	@Override
	public Iterable<?> getAvailableValues(CrudField field) {
		return this.getAvailableValues(field.getName());
	}

	protected BeanWrapper<T> getBean() {
		return bean;
	}
}
