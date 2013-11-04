package ar.com.oxen.nibiru.crud.manager.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

import ar.com.oxen.commons.bean.api.PropertyDescriptor;
import ar.com.oxen.commons.bean.api.WrapperFactory;
import ar.com.oxen.nibiru.crud.bean.annotation.Show;
import ar.com.oxen.nibiru.crud.bean.annotation.Widget;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.crud.manager.api.WidgetType;
import ar.com.oxen.nibiru.crud.utils.SimpleCrudField;

class AbstractJpaCrudComponent<T> {
	private EntityManager entityManager;
	private Class<T> persistentClass;
	private WrapperFactory wrapperFactory;
	private String pkName;

	public AbstractJpaCrudComponent(EntityManager entityManager,
			Class<T> persistentClass, WrapperFactory wrapperFactory) {
		super();
		this.entityManager = entityManager;
		this.persistentClass = persistentClass;
		this.wrapperFactory = wrapperFactory;
		this.pkName = this.searhPkName();
	}

	List<CrudField> getFormFields() {
		return this.fieldNamesToCrudFields(new ShowValidator() {
			@Override
			public boolean mustShow(Show show) {
				return show.inForm();
			}
		});
	}

	private List<PropertyDescriptor> getPersistentProperties() {
		List<PropertyDescriptor> descriptors = new LinkedList<PropertyDescriptor>();
		Class<?> currentClass = this.persistentClass;
		while (currentClass != null
				&& currentClass.isAnnotationPresent(Entity.class)) {
			Collections.addAll(descriptors,
					this.wrapperFactory.wrapClass(currentClass)
							.getPropertyDescriptors());
			currentClass = currentClass.getSuperclass();
		}

		return descriptors;
	}

	List<CrudField> fieldNamesToCrudFields(ShowValidator showValidator) {
		List<PropertyDescriptor> descriptors = new LinkedList<PropertyDescriptor>();

		for (PropertyDescriptor descriptor : this.getPersistentProperties()) {
			Show show = descriptor.getAnnotation(Show.class);
			if (show != null && showValidator.mustShow(show)) {
				descriptors.add(descriptor);
			}
		}

		Collections.sort(descriptors, new Comparator<PropertyDescriptor>() {
			@Override
			public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
				int order1 = o1.getAnnotation(Show.class).order();
				int order2 = o2.getAnnotation(Show.class).order();

				if (order1 > order2) {
					return 1;
				} else if (order1 > order2) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		List<CrudField> fields = new ArrayList<CrudField>(descriptors.size());
		for (PropertyDescriptor descriptor : descriptors) {
			boolean readonly = false;
			int maxLength = -1;
			WidgetType widgetType = null;
			String tab = CrudField.FormInfo.GENERAL_TAB;
			String[] values = null;

			Show show = descriptor.getAnnotation(Show.class);
			Widget crudWidget = descriptor.getAnnotation(Widget.class);
			if (crudWidget != null) {
				readonly = crudWidget.readonly();
				widgetType = crudWidget.type();
				maxLength = crudWidget.maxLength();
				tab = crudWidget.tab();
				values = crudWidget.values();
			}

			if (!this.isBasicType(descriptor.getType())) {
				if (Collection.class.isAssignableFrom(descriptor.getType())) {
					if (widgetType == null) {
						widgetType = WidgetType.MULTISELECT;
					}
				} else {
					if (widgetType == null) {
						widgetType = WidgetType.COMBO_BOX;
					}
				}
			}

			if (widgetType == null) {
				if (Date.class.equals(descriptor.getType())) {
					widgetType = WidgetType.DATE_FIELD;
				} else if (Boolean.class.equals(descriptor.getType())) {
					widgetType = WidgetType.CHECK_BOX;
				} else {
					widgetType = WidgetType.TEXT_FIELD;
				}
			}

			fields.add(new SimpleCrudField(descriptor.getName(), descriptor
					.getType(), new SimpleCrudField.SimpleListInfo(show
					.columWidth()), new SimpleCrudField.SimpleFormInfo(
					widgetType, readonly, maxLength, tab, values)));
		}

		return fields;
	}

	private boolean isBasicType(Class<?> type) {
		return type.isPrimitive() || String.class.isAssignableFrom(type)
				|| Number.class.isAssignableFrom(type)
				|| Boolean.class.isAssignableFrom(type)
				|| Date.class.isAssignableFrom(type);
	}

	private String searhPkName() {
		for (PropertyDescriptor descriptor : this.getPersistentProperties()) {
			if (descriptor.getAnnotation(Id.class) != null) {
				return descriptor.getName();
			}
		}
		throw new IllegalStateException("No ID defined for "
				+ this.persistentClass + " class");
	}

	interface ShowValidator {
		boolean mustShow(Show show);
	}

	EntityManager getEntityManager() {
		return entityManager;
	}

	Class<T> getPersistentClass() {
		return persistentClass;
	}

	WrapperFactory getWrapperFactory() {
		return wrapperFactory;
	}

	String getPkName() {
		return pkName;
	}
}
