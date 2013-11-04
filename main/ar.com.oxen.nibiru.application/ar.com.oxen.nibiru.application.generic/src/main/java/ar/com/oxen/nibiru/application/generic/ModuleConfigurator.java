package ar.com.oxen.nibiru.application.generic;

import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.application.api.ApplicationPresenterFactory;
import ar.com.oxen.nibiru.application.api.ApplicationViewFactory;
import ar.com.oxen.nibiru.application.api.about.AboutEvent;
import ar.com.oxen.nibiru.application.api.main.MainWindowShownEvent;
import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;
import ar.com.oxen.nibiru.security.api.SuccessfulLoginEvent;
import ar.com.oxen.nibiru.ui.api.extension.MenuItemExtension;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleMenuItemExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleSubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.mvp.SimpleEventBusClickHandler;

public class ModuleConfigurator
		extends
		AbstractModuleConfigurator<ApplicationViewFactory, ApplicationPresenterFactory> {
	private final static String ABOUT_MENU = "ar.com.oxen.nibiru.menu.about";

	@Override
	protected void configure() {
		this.registerExtension(new SimpleSubMenuExtension("about", ABOUT_MENU,
				99), "ar.com.oxen.nibiru.menu", SubMenuExtension.class);

		this.registerExtension(new SimpleMenuItemExtension("aboutNibiru", 20,
				new SimpleEventBusClickHandler(this.getEventBus(),
						AboutEvent.class, null)), ABOUT_MENU,
				MenuItemExtension.class);
	}

	@EventHandlerMethod
	public void onSuccessfulLogin(SuccessfulLoginEvent event) {
		activate(getViewFactory().buildMainView(), getPresenterFactory()
				.buildMainPresenter());
		this.getEventBus().fireEvent(new MainWindowShownEvent());
	}

	@EventHandlerMethod
	public void onAboutSelected(AboutEvent event) {
		activate(getViewFactory().buildAboutView(), getPresenterFactory()
				.buildAboutPresenter());
	}
}
