package ar.com.oxen.nibiru.transaction.api;

/**
 * A callback for executing login inside a transaction.
 * 
 * @param <T>
 *            The type of the objet to be returned
 */
public interface TransactionCallback<T> {
	T doInTransaction();
}
