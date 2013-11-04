package ar.com.oxen.nibiru.ui.utils.mvp;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public abstract class AbstractPresenter<V extends View> implements Presenter<V> {
	private V view;
	private EventBus eventBus;

	protected AbstractPresenter(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}

	@Override
	public void setView(V view) {
		this.view = view;
	}

	protected V getView() {
		return view;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	protected void configureClose(HasCloseWidget hasCloseWidget) {
		hasCloseWidget.getCloseHandler().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				getView().close();
			}
		});
	}
}
