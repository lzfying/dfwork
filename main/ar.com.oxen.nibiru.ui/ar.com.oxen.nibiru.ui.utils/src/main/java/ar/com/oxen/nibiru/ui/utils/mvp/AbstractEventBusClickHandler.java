package ar.com.oxen.nibiru.ui.utils.mvp;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;

public abstract class AbstractEventBusClickHandler implements ClickHandler {
	private EventBus eventBus;

	public AbstractEventBusClickHandler() {
		super();
	}

	public AbstractEventBusClickHandler(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

}
