package ar.com.oxen.nibiru.license.module.ui.impl;

import javax.inject.Inject;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.license.api.HardwareIdProvider;
import ar.com.oxen.commons.license.api.LicenseSerializer;
import ar.com.oxen.commons.license.impl.DefaultLicenseInfo;
import ar.com.oxen.nibiru.license.module.LicenseModule;
import ar.com.oxen.nibiru.license.module.ui.LicensePresenterFactory;
import ar.com.oxen.nibiru.license.module.ui.LicenseRequestView;
import ar.com.oxen.nibiru.license.store.api.LicenseStoreManager;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

import com.google.inject.Guice;

public class GenericLicensePresenterFactory implements LicensePresenterFactory {
	private LicenseStoreManager licenseStoreManager;
	private EventBus eventBus;

	@Inject
	private LicenseSerializer<DefaultLicenseInfo> licenseSerializer;

	@Inject
	private HardwareIdProvider hardwareIdProvider;

	public GenericLicensePresenterFactory() {
		super();
		Guice.createInjector(new LicenseModule()).injectMembers(this);
	}

	@Override
	public Presenter<LicenseRequestView> createLicenseRequestPresenter(
			boolean showInvalidLicenseMessage, Object callbackEvent,
			String callbackTopic) {
		return new GenericLicenseRequestPresenter(showInvalidLicenseMessage,
				callbackEvent, callbackTopic, this.eventBus,
				this.licenseStoreManager, this.licenseSerializer,
				this.hardwareIdProvider);
	}

	public void setLicenseStoreManager(LicenseStoreManager licenseStoreManager) {
		this.licenseStoreManager = licenseStoreManager;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
}
