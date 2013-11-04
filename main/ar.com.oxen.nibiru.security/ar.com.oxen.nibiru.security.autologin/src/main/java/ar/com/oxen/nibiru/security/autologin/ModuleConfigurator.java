package ar.com.oxen.nibiru.security.autologin;

import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.application.api.ApplicationStartEvent;
import ar.com.oxen.nibiru.security.api.SuccessfulLoginEvent;
import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;

public class ModuleConfigurator extends
		AbstractModuleConfigurator<Object, Object> {
	@EventHandlerMethod
	public void onApplicationStart(ApplicationStartEvent event) {
		this.getEventBus().fireEvent(new SuccessfulLoginEvent());
	}
}
