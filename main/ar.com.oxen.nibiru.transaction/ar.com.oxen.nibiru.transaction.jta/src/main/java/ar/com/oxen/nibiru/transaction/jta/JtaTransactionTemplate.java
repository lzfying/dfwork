package ar.com.oxen.nibiru.transaction.jta;

import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import ar.com.oxen.nibiru.transaction.api.TransactionCallback;
import ar.com.oxen.nibiru.transaction.api.TransactionException;
import ar.com.oxen.nibiru.transaction.api.TransactionTemplate;

public class JtaTransactionTemplate implements TransactionTemplate {
	private UserTransaction userTransaction;

	@Override
	public <T> T execute(EntityManager entityManager,
			TransactionCallback<T> callback) {
		if (!this.transactionActive()) {
			this.beginTransaction();
			entityManager.joinTransaction();

			T returnValue;
			try {
				returnValue = callback.doInTransaction();
			} catch (RuntimeException e) {
				this.rollbackTransaction();
				throw e;
			}
			this.commitTransaction();
			return returnValue;
		} else {
			entityManager.joinTransaction();
			return callback.doInTransaction();
		}
	}

	private boolean transactionActive() {
		try {
			int txStatus = this.userTransaction.getStatus();
			switch (txStatus) {
			case Status.STATUS_ACTIVE:
				return true;
			case Status.STATUS_NO_TRANSACTION:
				return false;
			default:
				throw new IllegalStateException("Invalid transaction state: "+txStatus);
			}
		} catch (SystemException e) {
			throw new TransactionException(e);
		}
	}

	private void beginTransaction() {
		try {
			this.userTransaction.begin();
		} catch (NotSupportedException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (SystemException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (RuntimeException e) {
			this.rollbackTransactionAndRethrow(e);
		}
	}

	private void commitTransaction() {
		try {
			this.userTransaction.commit();
		} catch (SecurityException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (RollbackException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (HeuristicMixedException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (HeuristicRollbackException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (SystemException e) {
			this.rollbackTransactionAndRethrow(e);
		} catch (RuntimeException e) {
			this.rollbackTransactionAndRethrow(e);
		}
	}

	private void rollbackTransaction() {
		try {
			this.userTransaction.rollback();
		} catch (IllegalStateException e) {
			throw new TransactionException(e);
		} catch (SecurityException e) {
			throw new TransactionException(e);
		} catch (SystemException e) {
			throw new TransactionException(e);
		}
	}

	private void rollbackTransactionAndRethrow(Exception e) {
		this.rollbackTransactionAndRethrow(new TransactionException(e));
	}

	private void rollbackTransactionAndRethrow(RuntimeException e) {
		this.rollbackTransaction();
		throw e;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}
}
