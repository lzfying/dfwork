package ar.com.oxen.nibiru.sample.mainview;

import ar.com.oxen.nibiru.application.api.ApplicationViewFactory;
import ar.com.oxen.nibiru.application.api.about.AboutView;
import ar.com.oxen.nibiru.application.api.main.MainView;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.application.generic.view.GenericAboutView;

public class CustomApplicationViewFactory implements ApplicationViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;
	private LocaleHolder localeHolder;

	@Override
	public MainView buildMainView() {
		return new CustomMainView(this.viewFactory, this.messageSource,
				this.localeHolder.getLocale());
	}

	@Override
	public AboutView buildAboutView() {
		return new GenericAboutView(this.viewFactory, this.messageSource);
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setLocaleHolder(LocaleHolder localeHolder) {
		this.localeHolder = localeHolder;
	}
}
