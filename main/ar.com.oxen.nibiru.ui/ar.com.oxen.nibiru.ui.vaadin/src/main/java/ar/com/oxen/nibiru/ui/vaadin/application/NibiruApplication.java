package ar.com.oxen.nibiru.ui.vaadin.application;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.eventbus.api.EventHandler;
import ar.com.oxen.nibiru.application.api.ApplicationStartEvent;
import ar.com.oxen.nibiru.application.api.ApplicationThemeChangeEvent;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;

import com.vaadin.Application;

public class NibiruApplication extends Application {
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -8241304827319878154L;
	private EventBus eventBus;
	private LocaleHolder localeHolder;
	private EventHandler<ApplicationThemeChangeEvent> applicationThemeChangeEventHandler;

	@Override
	public void init() {
		this.localeHolder.setLocale(this.getLocale());

		this.applicationThemeChangeEventHandler = new EventHandler<ApplicationThemeChangeEvent>() {
			@Override
			public void onEvent(ApplicationThemeChangeEvent event) {
				setTheme(event.getTheme());
			}
		};
		this.eventBus.addHandler(ApplicationThemeChangeEvent.class,
				this.applicationThemeChangeEventHandler);

		this.eventBus.fireEvent(new ApplicationStartEvent());
	}

	@Override
	public void close() {
		this.eventBus.removeHandler(this.applicationThemeChangeEventHandler);
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setLocaleHolder(LocaleHolder localeHolder) {
		this.localeHolder = localeHolder;
	}

}
