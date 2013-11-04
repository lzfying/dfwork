package ar.com.oxen.nibiru.license.module.ui.impl;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.license.api.HardwareIdProvider;
import ar.com.oxen.commons.license.api.License;
import ar.com.oxen.commons.license.api.LicenseSerializer;
import ar.com.oxen.commons.license.impl.DefaultLicenseInfo;
import ar.com.oxen.nibiru.license.module.ui.LicenseRequestView;
import ar.com.oxen.nibiru.license.store.api.LicenseStoreManager;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;
import ar.com.oxen.nibiru.ui.api.mvp.ValueChangeHandler;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class GenericLicenseRequestPresenter extends
		AbstractPresenter<LicenseRequestView> implements
		Presenter<LicenseRequestView> {
	private boolean showInvalidLicenseMessage;
	private LicenseStoreManager licenseStoreManager;
	private LicenseSerializer<DefaultLicenseInfo> licenseSerializer;
	private HardwareIdProvider hardwareIdProvider;
	private Object callbackEvent;
	private String callbackTopic;

	public GenericLicenseRequestPresenter(boolean showInvalidLicenseMessage,
			Object callbackEvent, String callbackTopic, EventBus eventBus,
			LicenseStoreManager licenseStoreManager,
			LicenseSerializer<DefaultLicenseInfo> licenseSerializer,
			HardwareIdProvider hardwareIdProvider) {
		super(eventBus);
		this.showInvalidLicenseMessage = showInvalidLicenseMessage;
		this.callbackEvent = callbackEvent;
		this.callbackTopic = callbackTopic;
		this.licenseStoreManager = licenseStoreManager;
		this.licenseSerializer = licenseSerializer;
		this.hardwareIdProvider = hardwareIdProvider;
	}

	@Override
	public void go() {
		LicenceValueChangeHandler changeHandler = new LicenceValueChangeHandler();
		this.getView().getCompanyChangeHandler()
				.setValueChangeHandler(changeHandler);
		this.getView().getModuleChangeHandler()
				.setValueChangeHandler(changeHandler);
		this.getView().getExpirationChangeHandler()
				.setValueChangeHandler(changeHandler);
		this.getView().getCodeChangeHandler()
				.setValueChangeHandler(changeHandler);

		String licenseString = this.licenseStoreManager
				.loadLicense(LicenseStoreManager.GENERIC_MODULE);

		if (licenseString != null) {
			this.getView().getLicenseAuthorization().setValue(licenseString);

			try {
				License<DefaultLicenseInfo> license = this.licenseSerializer
						.deserializeLicence(licenseString);
				this.getView().getCompanyName()
						.setValue(license.getInfo().getCustomerName());
				this.getView().getExpirationDate()
						.setValue(license.getInfo().getExpirationDate());
			} catch (Exception e) {
			}
		}

		this.getView().getAuthorize().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				licenseStoreManager.saveLicense(
						LicenseStoreManager.GENERIC_MODULE, getView()
								.getLicenseAuthorization().getValue());
				getView().close();
				if (callbackEvent != null) {
					getEventBus().fireEvent(callbackEvent, callbackTopic);
				}
			}
		});

		if (this.showInvalidLicenseMessage) {
			this.getView().showInvalidLicenseMessage();
		}
	}

	private class LicenceValueChangeHandler implements ValueChangeHandler {
		@Override
		public void onValueChange() {
			getView().getLicenseRequest().setValue(
					licenseSerializer
							.serializeLicenceInfo(new DefaultLicenseInfo(
									getView().getCompanyName().getValue(),
									getView().getModule().getValue(),
									getView().getExpirationDate().getValue(),
									getView().getCode().getValue(),
									hardwareIdProvider.getHardwareId())));
		}
	}
}
