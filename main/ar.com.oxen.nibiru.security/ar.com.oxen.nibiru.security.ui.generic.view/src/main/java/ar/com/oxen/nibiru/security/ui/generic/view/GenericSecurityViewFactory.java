package ar.com.oxen.nibiru.security.ui.generic.view;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.security.ui.api.ChangePasswordView;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.security.ui.api.SecurityViewFactory;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;

public class GenericSecurityViewFactory implements SecurityViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;

	@Override
	public LoginView createLoginView() {
		return new GenericLoginView(this.viewFactory, this.messageSource);
	}

	@Override
	public ChangePasswordView createChangePasswordView() {
		return new GenericChangePasswordView(this.viewFactory,
				this.messageSource);
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
