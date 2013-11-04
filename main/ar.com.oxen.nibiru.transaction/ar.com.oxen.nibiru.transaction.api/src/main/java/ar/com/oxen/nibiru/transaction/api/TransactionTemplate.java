package ar.com.oxen.nibiru.transaction.api;

import javax.persistence.EntityManager;

/**
 * A template for making calls inside a transaction.
 * 
 */
public interface TransactionTemplate {
	/**
	 * Executes the callback inside a transaction, joining the EntityManager.
	 * 
	 * @param entityManager
	 *            The entity manager
	 * @param callback
	 *            The callback
	 * @return The value returned by the callback
	 */
	<T> T execute(EntityManager entityManager, TransactionCallback<T> callback);
}
