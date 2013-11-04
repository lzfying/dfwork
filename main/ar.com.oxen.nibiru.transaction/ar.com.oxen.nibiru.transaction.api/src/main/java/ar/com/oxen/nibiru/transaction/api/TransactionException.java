package ar.com.oxen.nibiru.transaction.api;

public class TransactionException extends RuntimeException {
	private static final long serialVersionUID = -6195060367443049050L;

	public TransactionException(Throwable cause) {
		super(cause);
	}
}
