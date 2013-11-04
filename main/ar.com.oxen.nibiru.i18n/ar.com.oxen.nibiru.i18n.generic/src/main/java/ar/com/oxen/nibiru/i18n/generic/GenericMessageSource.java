package ar.com.oxen.nibiru.i18n.generic;

import java.util.List;
import java.util.Locale;

import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageProvider;
import ar.com.oxen.nibiru.i18n.api.MessageSource;

public class GenericMessageSource implements MessageSource {
	private LocaleHolder localeHolder;
	private List<MessageProvider> messageProviders;

	@Override
	public String getMessage(String code, Object... args) {
		return this.getMessage(code, this.localeHolder.getLocale(), args);
	}

	@Override
	public String getMessage(String code, Locale locale, Object... args) {
		String message = this.findMessage(code, locale, args);
		if (message != null) {
			return message;
		} else {
			return code;
		}
	}

	@Override
	public String findMessage(String code, Object... args) {
		return this.findMessage(code, this.localeHolder.getLocale(), args);
	}

	@Override
	public String findMessage(String code, Locale locale, Object... args) {
		for (MessageProvider messageProvider : this.messageProviders) {
			String message = messageProvider.getMessage(code, locale, args);
			if (message != null) {
				return message;
			}
		}

		return null;
	}

	public void setLocaleHolder(LocaleHolder localeHolder) {
		this.localeHolder = localeHolder;
	}

	public void setMessageProviders(List<MessageProvider> messageProviders) {
		this.messageProviders = messageProviders;
	}
}
