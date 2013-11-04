package ar.com.oxen.nibiru.license.module.ui;

import java.util.Date;

import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.HasValueChangeHandler;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public interface LicenseRequestView extends View {
	HasValue<String> getCompanyName();

	HasValueChangeHandler getCompanyChangeHandler();

	HasValue<String> getModule();

	HasValueChangeHandler getModuleChangeHandler();

	HasValue<Date> getExpirationDate();

	HasValueChangeHandler getExpirationChangeHandler();

	HasValue<String> getCode();

	HasValueChangeHandler getCodeChangeHandler();

	HasValue<String> getLicenseRequest();

	HasValue<String> getLicenseAuthorization();

	HasClickHandler getAuthorize();

	void showInvalidLicenseMessage();
}
