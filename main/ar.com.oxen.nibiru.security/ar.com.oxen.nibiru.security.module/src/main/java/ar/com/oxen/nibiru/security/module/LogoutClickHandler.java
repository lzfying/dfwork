package ar.com.oxen.nibiru.security.module;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.SuccessfulLogoutEvent;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractEventBusClickHandler;

public class LogoutClickHandler extends AbstractEventBusClickHandler {
	private AuthenticationService authenticationService;

	public LogoutClickHandler() {
		super();
	}

	public LogoutClickHandler(EventBus eventBus,
			AuthenticationService authenticationService) {
		super(eventBus);
		this.authenticationService = authenticationService;
	}

	@Override
	public void onClick() {
		this.authenticationService.logout();
		this.getEventBus().fireEvent(new SuccessfulLogoutEvent());
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
