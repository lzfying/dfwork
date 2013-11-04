package ar.com.oxen.nibiru.license.store.jpa;

import javax.persistence.EntityManager;

import ar.com.oxen.nibiru.license.store.api.LicenseStoreManager;
import ar.com.oxen.nibiru.license.store.jpa.domain.License;

public class JpaLicenseStoreManager implements LicenseStoreManager {
	private EntityManager entityManager;

	@Override
	public String loadLicense(String module) {
		License license = this.entityManager.find(License.class, module);
		if (license != null) {
			return license.getLicense();
		} else {
			return null;
		}
	}

	@Override
	public void saveLicense(String module, String license) {
		License licenseEntity = this.entityManager.find(License.class, module);
		if (licenseEntity == null) {
			licenseEntity = new License();
			licenseEntity.setModule(module);
		}
		licenseEntity.setLicense(license);
		this.entityManager.persist(licenseEntity);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
