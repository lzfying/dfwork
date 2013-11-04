package ar.com.oxen.nibiru.transaction.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ar.com.oxen.nibiru.transaction.api.TransactionCallback;
import ar.com.oxen.nibiru.transaction.api.TransactionTemplate;

public class JpaTransactionTemplate implements TransactionTemplate {
	@Override
	public <T> T execute(EntityManager entityManager,
			TransactionCallback<T> callback) {
		EntityTransaction transaction = entityManager.getTransaction();
		boolean newTransaction = !transaction.isActive();

		try {
			if (newTransaction) {
				transaction.begin();
			}
			T returnValue = callback.doInTransaction();
			if (newTransaction) {
				transaction.commit();
			}
			return returnValue;
			
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
}
