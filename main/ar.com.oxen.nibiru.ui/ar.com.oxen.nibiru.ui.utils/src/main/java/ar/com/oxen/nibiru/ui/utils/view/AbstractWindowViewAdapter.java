package ar.com.oxen.nibiru.ui.utils.view;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.HasTitle;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.ComponentContainer;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;

public class AbstractWindowViewAdapter<T extends Window> extends
		AbstractAdapter<T> implements View, HasCloseWidget, HasTitle {
	private Button closeButton;
	private ViewFactory viewFactory;
	private MessageSource messageSource;

	public AbstractWindowViewAdapter(T adapted, ViewFactory viewFactory,
			MessageSource messageSource) {
		super(adapted);
		this.viewFactory = viewFactory;
		this.messageSource = messageSource;
	}

	@Override
	public void show() {
		this.getAdapted().show();
	}

	@Override
	public void close() {
		this.getAdapted().close();
	}

	@Override
	public HasValue<String> getTitle() {
		return this.getAdapted();
	}

	@Override
	public HasClickHandler getCloseHandler() {
		return this.closeButton;
	}

	protected void addClose() {
		this.addClose(this.getAdapted());
	}

	protected void addClose(ComponentContainer container) {
		this.closeButton = this.viewFactory.buildButton();
		this.closeButton.setValue(this.messageSource
				.getMessage("ar.com.oxen.nibiru.app.close"));
		container.addComponent(this.closeButton);
	}

	protected ViewFactory getViewFactory() {
		return viewFactory;
	}

	protected MessageSource getMessageSource() {
		return messageSource;
	}
}
