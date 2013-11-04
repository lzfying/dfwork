package ar.com.oxen.nibiru.sample.system;



import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;

import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;
import ar.com.oxen.nibiru.sample.system.presenter.RolePresenterFactory;
import ar.com.oxen.nibiru.sample.system.view.RoleViewFactory;
import ar.com.oxen.nibiru.ui.api.extension.MenuItemExtension;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleMenuItemExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleSubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.mvp.SimpleEventBusClickHandler;

public class ModuleConfigurator extends AbstractModuleConfigurator<RoleViewFactory, RolePresenterFactory>{
	
	private final static String ROLE_MENU = "ar.com.oxen.nibiru.menu.role";
	
	@Override
	protected void configure() {
		this.registerExtension(new SimpleSubMenuExtension("sample.system", ROLE_MENU,
				200), "ar.com.oxen.nibiru.menu", SubMenuExtension.class);

		this.registerExtension(new SimpleMenuItemExtension("sample.role", 20,
				new SimpleEventBusClickHandler(this.getEventBus(),
						RoleEvent.class, null)), ROLE_MENU,
				MenuItemExtension.class);
	}
	
	
	@EventHandlerMethod
	public void onRoleSelected(RoleEvent event) {
		System.out.println("osssss");
		activate(getViewFactory().buildRoleView(), getPresenterFactory().buildRolePresenter()
				);
	}

}
