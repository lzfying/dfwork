package ar.com.oxen.nibiru.crud.manager.jpa;

import javax.persistence.EntityManager;

import ar.com.oxen.commons.bean.api.WrapperFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.transaction.api.TransactionTemplate;

public class JpaCrudFactory implements CrudFactory {
	private EntityManager entityManager;
	private WrapperFactory wrapperFactory;
	private AuthorizationService authorizationService;
	private TransactionTemplate transactionTemplate;
	private Profile profile;

	@Override
	public <T> CrudManager<T> createCrudManager(Class<T> persistentClass) {
		return new JpaCrudManager<T>(this.entityManager, persistentClass,
				this.wrapperFactory, this.authorizationService, this.profile);
	}

	@Override
	public <T> CrudActionExtension<T> createDefaultCrudActionExtension(
			Class<T> persistentClass) {
		return new JpaCrudActionExtension<T>(entityManager, persistentClass,
				wrapperFactory, this.transactionTemplate);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setWrapperFactory(WrapperFactory wrapperFactory) {
		this.wrapperFactory = wrapperFactory;
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
