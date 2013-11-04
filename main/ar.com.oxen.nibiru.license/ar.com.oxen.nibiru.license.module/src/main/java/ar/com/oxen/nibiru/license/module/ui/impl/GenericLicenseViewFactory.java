package ar.com.oxen.nibiru.license.module.ui.impl;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.license.module.ui.LicenseRequestView;
import ar.com.oxen.nibiru.license.module.ui.LicenseViewFactory;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;

public class GenericLicenseViewFactory implements LicenseViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;

	@Override
	public LicenseRequestView createLicenseRequestView() {
		return new GenericLicenseRequestView(this.viewFactory,
				this.messageSource);
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
