package ar.com.oxen.nibiru.sample.loginview;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.security.ui.api.ChangePasswordView;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.security.ui.api.SecurityViewFactory;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;
import ar.com.oxen.nibiru.security.ui.generic.view.GenericChangePasswordView;

public class CustomSecurityViewFactory implements SecurityViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;
	private ApplicationAccessor applicationAccessor;
	
	@Override
	public LoginView createLoginView() {
		return new CustomLoginView(this.applicationAccessor,this.viewFactory, this.messageSource);
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
	
	public void setApplicationAccessor(ApplicationAccessor applicationAccessor) {
		this.applicationAccessor = applicationAccessor;
	}
}
