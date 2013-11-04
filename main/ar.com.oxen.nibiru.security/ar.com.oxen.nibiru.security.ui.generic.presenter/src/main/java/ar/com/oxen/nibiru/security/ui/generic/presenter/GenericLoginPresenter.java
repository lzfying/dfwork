package ar.com.oxen.nibiru.security.ui.generic.presenter;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.BadCredentialsException;
import ar.com.oxen.nibiru.security.api.SuccessfulLoginEvent;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class GenericLoginPresenter extends AbstractPresenter<LoginView> {
	private AuthenticationService authenticationService;

	public GenericLoginPresenter(AuthenticationService authenticationService,
			EventBus eventBus) {
		super(eventBus);
		this.authenticationService = authenticationService;
	}

	@Override
	public void go() {
		this.getView().getLoginButton().setClickHandler(new ClickHandler() {

			@Override
			public void onClick() {
				try {
					authenticationService.login(getView().getUserField()
							.getValue(), getView().getPasswordField()
							.getValue());
					GenericLoginPresenter.this.getEventBus().fireEvent(
							new SuccessfulLoginEvent());
				} catch (BadCredentialsException e) {
					getView().getErrorMessage().setValue(
							"ar.com.oxen.nibiru.app.invalidCredentials");
				}
			}
		});
	}
}
