package ar.com.oxen.nibiru.i18n.session;

import java.util.Locale;

import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.session.api.Session;

public class SessionLocaleHolder implements LocaleHolder {
	private Session session;
	private final static String LOCALE_KEY = "ar.com.oxen.nibiru.i18n.locale";

	@Override
	public Locale getLocale() {
		return this.session.get(LOCALE_KEY);
	}

	@Override
	public void setLocale(Locale newLocale) {
		this.session.put(LOCALE_KEY, newLocale);
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
