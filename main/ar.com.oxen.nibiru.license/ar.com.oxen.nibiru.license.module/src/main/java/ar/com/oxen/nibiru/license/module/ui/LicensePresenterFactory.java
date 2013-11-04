package ar.com.oxen.nibiru.license.module.ui;

import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

public interface LicensePresenterFactory {
	Presenter<LicenseRequestView> createLicenseRequestPresenter(
			boolean showInvalidLicenseMessage, Object callbackEvent,
			String callbackTopic);
}
