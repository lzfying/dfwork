package ar.com.oxen.nibiru.jpa;

import javax.persistence.EntityManager;

public interface ConversationEntityManager extends EntityManager {
	/**
	 * This method is a workaround. Aries JPA implementation calls
	 * internalClose(), so it must be defined in the interface (even it is not
	 * part of EntityManager). It is called (dinamically) as destroy-method in
	 * blueprint container.
	 */
	void internalClose();
}
