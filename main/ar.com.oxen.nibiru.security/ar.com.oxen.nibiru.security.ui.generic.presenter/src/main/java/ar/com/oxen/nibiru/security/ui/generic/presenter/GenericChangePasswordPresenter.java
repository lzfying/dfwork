package ar.com.oxen.nibiru.security.ui.generic.presenter;

import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.HashService;
import ar.com.oxen.nibiru.security.manager.api.InvalidOldPassword;
import ar.com.oxen.nibiru.security.manager.api.SecurityManager;
import ar.com.oxen.nibiru.security.ui.api.ChangePasswordView;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class GenericChangePasswordPresenter extends
		AbstractPresenter<ChangePasswordView> implements
		Presenter<ChangePasswordView> {
	private AuthenticationService authenticationService;
	private SecurityManager securityManager;
	private HashService hashService;

	public GenericChangePasswordPresenter(
			AuthenticationService authenticationService,
			SecurityManager securityManager, HashService hashService) {
		super(null);
		this.authenticationService = authenticationService;
		this.securityManager = securityManager;
		this.hashService = hashService;
	}

	@Override
	public void go() {
		this.getView().getChangePassword().setClickHandler(new ClickHandler() {

			@Override
			public void onClick() {
				changePassword();
			}
		});
	}

	private void changePassword() {
		try {
			String oldPassword = getView().getOldPassword().getValue();
			if (oldPassword == null) {
				oldPassword = "";
			}
			String newPassword = getView().getNewPassword().getValue();
			if (newPassword == null) {
				newPassword = "";
			}
			String passwordConfirmation = getView().getPasswordConfirmation()
					.getValue();
			if (passwordConfirmation == null) {
				passwordConfirmation = "";
			}
			if (!newPassword.equals(passwordConfirmation)) {
				getView().notifyPasswordMismatch();
				return;
			}

			securityManager
					.changePassword(
							authenticationService.getLoggedUserName(),
							oldPassword.equals("") ? "" : hashService
									.hash(oldPassword), hashService
									.hash(newPassword));

			getView().close();
		} catch (InvalidOldPassword e) {
			getView().notifyBadOldPassword();
		}
	}
}
