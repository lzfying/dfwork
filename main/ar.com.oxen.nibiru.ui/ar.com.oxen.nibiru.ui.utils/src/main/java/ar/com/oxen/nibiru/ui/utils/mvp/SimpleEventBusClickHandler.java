package ar.com.oxen.nibiru.ui.utils.mvp;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.exception.api.ExceptionWrapper;

public class SimpleEventBusClickHandler extends AbstractEventBusClickHandler {
	private Class<?> eventClass;
	private String topic;

	public SimpleEventBusClickHandler() {
		super();
	}

	public SimpleEventBusClickHandler(EventBus eventBus, Class<?> eventClass,
			String topic) {
		super(eventBus);
		this.eventClass = eventClass;
		this.topic = topic;
	}

	@Override
	public void onClick() {
		try {
			this.getEventBus().fireEvent(this.eventClass.newInstance(),
					this.topic);
		} catch (InstantiationException e) {
			throw new ExceptionWrapper(e);
		} catch (IllegalAccessException e) {
			throw new ExceptionWrapper(e);
		}
	}

	public void setEventClass(Class<?> eventClass) {
		this.eventClass = eventClass;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
