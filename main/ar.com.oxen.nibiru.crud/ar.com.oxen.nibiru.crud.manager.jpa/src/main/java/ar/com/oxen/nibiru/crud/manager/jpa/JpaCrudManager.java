package ar.com.oxen.nibiru.crud.manager.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mvel2.MVEL;

import ar.com.oxen.commons.bean.api.WrapperFactory;
import ar.com.oxen.nibiru.crud.bean.annotation.Filter;
import ar.com.oxen.nibiru.crud.bean.annotation.Show;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;

public class JpaCrudManager<T> extends AbstractJpaCrudComponent<T> implements
		CrudManager<T> {
	private String filter;
	private AuthorizationService authorizationService;
	private Profile profile;

	public JpaCrudManager(EntityManager entityManager,
			Class<T> persistentClass, WrapperFactory wrapperFactory,
			AuthorizationService authorizationService, Profile profile) {
		super(entityManager, persistentClass, wrapperFactory);

		this.authorizationService = authorizationService;
		this.profile = profile;

		Filter filterAnnotation = persistentClass.getAnnotation(Filter.class);
		if (filterAnnotation != null) {
			this.filter = filterAnnotation.value();
		}
	}

	@Override
	public String getEntityTypeName() {
		return this.getPersistentClass().getName();
	}

	@Override
	public List<CrudField> getListFields() {
		return this
				.fieldNamesToCrudFields(new AbstractJpaCrudComponent.ShowValidator() {
					@Override
					public boolean mustShow(Show show) {
						return show.inList();
					}
				});
	}

	@Override
	public List<CrudEntity<T>> findAll() {
		List<T> beans = this.findAll(this.getPersistentClass());
		return this.beansToCrudEntities(beans);
	}

	@Override
	public CrudEntity<T> findById(Object id) {
		T bean = this.getEntityManager().find(this.getPersistentClass(), id);
		if (bean != null) {
			return this.beanToCrudEntity(bean);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrudEntity<T>> findByfield(String field, Object value) {
		StringBuilder sb = new StringBuilder();

		sb.append("select o from ");
		sb.append(this.getPersistentClass().getName());
		sb.append(" o where o.");
		sb.append(field);
		sb.append(" = :field");

		String computedFilter = this.computeFilter();
		if (computedFilter != null) {
			sb.append(" and ");
			sb.append(computedFilter);
		}

		Query query = this.getEntityManager().createQuery(sb.toString());
		query.setParameter("field", value);
		return this.beansToCrudEntities(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	private <K> List<K> findAll(Class<?> type) {
		StringBuilder sb = new StringBuilder();

		sb.append("select o from ");
		sb.append(type.getName());
		sb.append(" o");

		String computedFilter = this.computeFilter();
		if (computedFilter != null) {
			sb.append(" where ");
			sb.append(computedFilter);
		}

		Query query = this.getEntityManager().createQuery(sb.toString());
		return query.getResultList();
	}

	private String computeFilter() {
		if (this.filter != null) {
			Map<String, Object> environment = new HashMap<String, Object>();
			environment.put("authz", this.authorizationService);
			
			if (this.profile != null ) {
				environment.put("profile", this.profile);
			}

			Object computedFilter = MVEL.eval(this.filter, environment);

			if (computedFilter == null
					|| computedFilter.toString().trim().equals("")) {
				return null;
			} else {
				return computedFilter.toString();
			}
		} else {
			return null;
		}
	}

	private <K> List<CrudEntity<K>> beansToCrudEntities(List<K> beans) {
		List<CrudEntity<K>> crudEntities = new ArrayList<CrudEntity<K>>(
				beans.size());
		for (K bean : beans) {
			crudEntities.add(this.beanToCrudEntity(bean));
		}

		return crudEntities;
	}

	private <K> CrudEntity<K> beanToCrudEntity(K bean) {
		return new JpaCrudEntity<K>(this.getWrapperFactory().wrapBean(bean),
				this.getEntityManager(), this.getPkName(), this.getFormFields());
	}
}
