package ar.com.oxen.nibiru.security.module;

import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.application.api.ApplicationStartEvent;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.utils.AbstractCrudModuleConfigurator;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.AuthorizationService;
import ar.com.oxen.nibiru.security.api.SuccessfulLogoutEvent;
import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;
import ar.com.oxen.nibiru.security.manager.jpa.domain.RoleGroup;
import ar.com.oxen.nibiru.security.manager.jpa.domain.User;
import ar.com.oxen.nibiru.security.ui.api.SecurityPresenterFactory;
import ar.com.oxen.nibiru.security.ui.api.SecurityViewFactory;
import ar.com.oxen.nibiru.ui.api.extension.MenuItemExtension;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleMenuItemExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleSubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.mvp.SimpleEventBusClickHandler;

public class ModuleConfigurator extends AbstractCrudModuleConfigurator {
	private static final String MENU_EXTENSION = "ar.com.oxen.nibiru.security.db";

	private CrudFactory crudFactory;
	private AuthenticationService authenticationService;
	private SecurityPresenterFactory securityPresenterFactory;
	private SecurityViewFactory securityViewFactory;

	private final static String[] OPERATOR_ROLES = { AuthorizationService.OPERATOR_ROLE };
	private final static String[] ADMINISTRATOR_ROLES = { AuthorizationService.ADMINISTRATOR_ROLE };

	@Override
	protected void configure() {

		CrudManager<User> userCrudManager = this.crudFactory
				.createCrudManager(User.class);
		CrudActionExtension<User> userCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(User.class);

		CrudManager<Role> roleCrudManager = this.crudFactory
				.createCrudManager(Role.class);
		CrudActionExtension<Role> roleCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(Role.class);

		CrudManager<RoleGroup> groupCrudManager = this.crudFactory
				.createCrudManager(RoleGroup.class);
		CrudActionExtension<RoleGroup> groupCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(RoleGroup.class);

		this.registerExtension(new SimpleSubMenuExtension("security",
				MENU_EXTENSION, 80), "ar.com.oxen.nibiru.menu",
				SubMenuExtension.class);

		this.registerExtension(new SimpleMenuItemExtension("security.logout",
				-20, new LogoutClickHandler(this.getEventBus(),
						this.authenticationService)), MENU_EXTENSION,
				MenuItemExtension.class);

		this.registerExtension(new SimpleMenuItemExtension(
				"security.changePassword", -10, new SimpleEventBusClickHandler(
						this.getEventBus(), ChangePasswordEvent.class, null),
				OPERATOR_ROLES), MENU_EXTENSION, MenuItemExtension.class);

		this.addCrudWithMenu("security.users", MENU_EXTENSION, userCrudManager,
				userCrudActionExtension, ADMINISTRATOR_ROLES);

		this.addCrudWithMenu("security.roles", MENU_EXTENSION, roleCrudManager,
				roleCrudActionExtension, ADMINISTRATOR_ROLES);

		this.addCrudWithMenu("security.groups", MENU_EXTENSION,
				groupCrudManager, groupCrudActionExtension, ADMINISTRATOR_ROLES);

	}

	@EventHandlerMethod
	public void onApplicationStart(ApplicationStartEvent event) {
		activate(this.securityViewFactory.createLoginView(),
				this.securityPresenterFactory.createLoginPresenter());
	}

	@EventHandlerMethod
	public void onChangePassword(ChangePasswordEvent event) {
		this.activate(this.securityViewFactory.createChangePasswordView(),
				this.securityPresenterFactory.createChangePasswordPresenter());
	}

	@EventHandlerMethod
	public void onSuccessfulLogout(SuccessfulLogoutEvent event) {
		activate(this.securityViewFactory.createLoginView(),
				this.securityPresenterFactory.createLoginPresenter());
	}

	public void setCrudFactory(CrudFactory crudFactory) {
		this.crudFactory = crudFactory;
	}

	public void setSecurityPresenterFactory(
			SecurityPresenterFactory securityPresenterFactory) {
		this.securityPresenterFactory = securityPresenterFactory;
	}

	public void setSecurityViewFactory(SecurityViewFactory securityViewFactory) {
		this.securityViewFactory = securityViewFactory;
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
