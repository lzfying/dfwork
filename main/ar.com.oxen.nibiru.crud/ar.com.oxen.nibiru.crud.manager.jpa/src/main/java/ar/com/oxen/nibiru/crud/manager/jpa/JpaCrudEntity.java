package ar.com.oxen.nibiru.crud.manager.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.mvel2.MVEL;

import ar.com.oxen.commons.bean.api.BeanWrapper;
import ar.com.oxen.commons.bean.api.PropertyDescriptor;
import ar.com.oxen.nibiru.crud.bean.annotation.Widget;
import ar.com.oxen.nibiru.crud.bean.manager.AbstractBeanCrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;

public class JpaCrudEntity<T> extends AbstractBeanCrudEntity<T> {
	private EntityManager entityManager;

	public JpaCrudEntity(BeanWrapper<T> bean, EntityManager entityManager,
			String pkName, List<CrudField> formFields) {
		super(bean, pkName, formFields);
		this.entityManager = entityManager;
	}

	@Override
	public Iterable<?> getAvailableValues(String fieldName) {
		Iterable<Object> availableValues = null;

		PropertyDescriptor descriptor = this.getBean().getPropertyDescriptor(
				fieldName);

		if (!this.isBasicType(descriptor.getType())) {
			Widget widget = descriptor.getAnnotation(Widget.class);

			String valuesFilterExpression = widget != null ? widget
					.valuesFilterExpression() : "";

			String filter = !valuesFilterExpression.equals("") ? MVEL
					.evalToString(valuesFilterExpression, this.getEntity())
					: "";

			if (Collection.class.isAssignableFrom(descriptor.getType())) {
				availableValues = this.findByFilter(
						this.getCollectionType(descriptor.getName()), filter);
			} else {
				availableValues = this.findByFilter(descriptor.getType(),
						filter);
			}
		}

		return availableValues;
	}

	private boolean isBasicType(Class<?> type) {
		return type.isPrimitive() || String.class.isAssignableFrom(type)
				|| Number.class.isAssignableFrom(type)
				|| Boolean.class.isAssignableFrom(type)
				|| Date.class.isAssignableFrom(type);
	}

	private Class<?> getCollectionType(String fieldName) {
		try {
			Class<?> currentClass = this.getEntity().getClass();
			Field field = null;

			while (field == null && currentClass != null
					&& currentClass.isAnnotationPresent(Entity.class)) {
				try {
					field = currentClass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
				}
				currentClass = currentClass.getSuperclass();
			}

			if (field != null) {
				ParameterizedType parametrizedType = (ParameterizedType) field
						.getGenericType();

				return (Class<?>) parametrizedType.getActualTypeArguments()[0];
			} else {
				throw new IllegalArgumentException("Field not found:"
						+ fieldName);
			}
		} catch (SecurityException e) {
			throw new IllegalArgumentException(fieldName, e);
		}
	}

	@SuppressWarnings("unchecked")
	private <K> List<K> findByFilter(Class<?> type, String filter) {
		Query query = this.entityManager.createQuery("select o from "
				+ type.getName() + " o"
				+ (filter.equals("") ? "" : (" where " + filter)));

		return query.getResultList();
	}
}
