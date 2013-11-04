package ar.com.oxen.nibiru.security.ui.generic.presenter;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.HashService;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.ui.api.ChangePasswordView;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.security.ui.api.SecurityPresenterFactory;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

public class GenericSecurityPresenterFactory implements
		SecurityPresenterFactory {
	private AuthenticationService authenticationService;
	private SecurityManager securityManager;
	private HashService hashService;
	private EventBus eventBus;

	public GenericSecurityPresenterFactory() {
		super();
	}

	@Override
	public Presenter<LoginView> createLoginPresenter() {
		return new GenericLoginPresenter(this.authenticationService,
				this.eventBus);
	}

	@Override
	public Presenter<ChangePasswordView> createChangePasswordPresenter() {
		return new GenericChangePasswordPresenter(this.authenticationService,
				this.securityManager, this.hashService);
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public void setHashService(HashService hashService) {
		this.hashService = hashService;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
}
