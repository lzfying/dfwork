package ar.com.oxen.nibiru.i18n.generic;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ar.com.oxen.nibiru.i18n.api.MessageProvider;

public class ResourceBundleMessageProvider implements MessageProvider {
	private String baseName;
	private ClassLoader resourceClassLoader;

	@Override
	public String getMessage(String code, Locale locale, Object... args) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(this.baseName,
				locale, this.resourceClassLoader);

		try {
			String message = resourceBundle.getString(code);
			if (message != null) {
				return MessageFormat.format(message, args);
			} else {
				return null;
			}
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public void setResourceClassLoader(ClassLoader resourceClassLoader) {
		this.resourceClassLoader = resourceClassLoader;
	}
}
