package ar.com.oxen.nibiru.jpa;

import javax.persistence.EntityManager;

interface EntityManagerCreator {
	EntityManager create();
}