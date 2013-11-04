package ar.com.oxen.nibiru.ui.vaadin.application;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.http.utils.SessionHolder;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;

public class SimpleApplicationAccessor implements ApplicationAccessor {
	private EventBus eventBus;
	private LocaleHolder localeHolder;

	@Override
	public Application createApplication() {
		NibiruApplication nibiruApplication = new NibiruApplication();
		nibiruApplication.setEventBus(this.eventBus);
		nibiruApplication.setLocaleHolder(this.localeHolder);
		return nibiruApplication;
	}

	@Override
	public Application getApplication() {
		WebApplicationContext context = WebApplicationContext
				.getApplicationContext(SessionHolder.getSession());

		if (context.getApplications().size() > 0) {
			return context.getApplications().iterator().next();
		} else {
			throw new IllegalStateException("No Vaadin App on context");
		}
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setLocaleHolder(LocaleHolder localeHolder) {
		this.localeHolder = localeHolder;
	}
}
