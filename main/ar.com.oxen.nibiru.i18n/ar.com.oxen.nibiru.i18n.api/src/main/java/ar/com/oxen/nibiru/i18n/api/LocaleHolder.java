package ar.com.oxen.nibiru.i18n.api;

import java.util.Locale;

/**
 * Service used to access the user locale.
 * 
 */
public interface LocaleHolder {
	/**
	 * Gets the user locale.
	 * 
	 * @return The locale
	 */
	Locale getLocale();

	/**
	 * Sets the user locale.
	 * 
	 * @param newLocale
	 *            The locale
	 */
	void setLocale(Locale newLocale);
}
