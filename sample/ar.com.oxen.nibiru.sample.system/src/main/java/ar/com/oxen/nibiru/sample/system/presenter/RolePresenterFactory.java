package ar.com.oxen.nibiru.sample.system.presenter;




import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.sample.system.api.IRoleView;
import ar.com.oxen.nibiru.sample.system.view.RolePresenter;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.Profile;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

public class RolePresenterFactory {
	//private ExtensionPointManager extensionPointManager;
	//private AuthorizationService authorizationService;
	public Presenter<IRoleView> buildRolePresenter() {
		return new RolePresenter();
	}

}
