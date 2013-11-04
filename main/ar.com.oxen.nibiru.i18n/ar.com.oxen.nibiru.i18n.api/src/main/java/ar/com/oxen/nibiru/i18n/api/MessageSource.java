package ar.com.oxen.nibiru.i18n.api;

import java.util.Locale;

/**
 * Service for accessing i18n messages. Typically a view from a module will
 * access this service. Internally, implementation of this module should access
 * the current user locale with {@link LocaleHolder} and delegate on N
 * {@link MessageProvider}s in order to look for the searched message.
 */
public interface MessageSource {
	/**
	 * Gets a i18n message
	 * 
	 * @param code
	 *            The message code
	 * @param args
	 *            The message arguments
	 * @return The translated an parsed message. If the message is not found, it
	 *         returns the code.
	 */
	String getMessage(String code, Object... args);

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
	 *         the code is returned.
	 */
	String getMessage(String code, Locale locale, Object... args);

	/**
	 * Gets a i18n message
	 * 
	 * @param code
	 *            The message code
	 * @param args
	 *            The message arguments
	 * @return The translated an parsed message. If the message is not found, it
	 *         returns null.
	 */
	String findMessage(String code, Object... args);

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
	String findMessage(String code, Locale locale, Object... args);
}
