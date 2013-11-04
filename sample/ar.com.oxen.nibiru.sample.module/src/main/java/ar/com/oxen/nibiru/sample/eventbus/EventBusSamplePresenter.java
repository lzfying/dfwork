package ar.com.oxen.nibiru.sample.eventbus;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.sample.eventbus.EventBusSamplePresenter.Display;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.CloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class EventBusSamplePresenter extends AbstractPresenter<Display> {
	public interface Display extends View {
		HasValue<String> getMessageInput();

		HasValue<String> getMessageOutput();

		HasClickHandler getSendMessage();

		HasCloseHandler getClose();
	}

	public EventBusSamplePresenter(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	public void go() {
		this.getEventBus().subscribeAnnotatedObject(this);

		this.getView().getSendMessage().setClickHandler(new ClickHandler() {

			@Override
			public void onClick() {
				sendMessage();
			}
		});

		this.getView().getClose().setCloseHandler(new CloseHandler() {

			@Override
			public void onClose() {
				getEventBus().unsubscribeAnnotatedObject(this);
			}
		});
	}

	private void sendMessage() {
		this.getEventBus().fireEvent(
				new SampleEvent(this.getView().getMessageInput().getValue()));
	}

	@EventHandlerMethod
	public void onMessage(SampleEvent event) {
		this.getView().getMessageOutput().setValue(event.getMessage());
	}
}
