package ar.com.oxen.nibiru.i18n.api;

import java.util.Locale;

/**
 * A message provider. This interface is provided in order to allow i18n
 * modularity. Each module could provide its own MessageProvider. All the
 * MessageProviders would be consolidated by a single, generic
 * {@link MessageSource}.
 */
public interface MessageProvider {
	/**
	 * Returns a 18n message
	 * 
	 * @param code
	 *            The message code
	 * @param locale
	 *            The locale
	 * @param args
	 *            The message arguments
	 * @return The translated an parsed message. If the message is not found,
	 *         null is returned.
	 */
	String getMessage(String code, Locale locale, Object... args);
}
