package ar.com.oxen.nibiru.ui.utils.mvp;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;

public class HasValueI18nDecorator implements HasValue<String> {
	private HasValue<String> decorated;
	private MessageSource messageSource;
	private String code;

	public HasValueI18nDecorator(HasValue<String> decorated,
			MessageSource messageSource) {
		super();
		this.decorated = decorated;
		this.messageSource = messageSource;
	}

	@Override
	public String getValue() {
		return this.code;
	}

	@Override
	public void setValue(String value) {
		this.code = value;
		this.decorated.setValue(this.messageSource.getMessage(this.code));
	}

}
