package ar.com.oxen.nibiru.sample.eventbus;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class EventBusSampleView extends AbstractWindowViewAdapter<Window>
		implements EventBusSamplePresenter.Display {
	private TextField<String> messageInput;
	private Label<String> messageOutput;
	private Button sendMessage;

	public EventBusSampleView(ViewFactory viewFactory,
			MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);
		Label<String> title = viewFactory.buildLabel(String.class);

		String titleText = messageSource
				.getMessage("ar.com.oxen.nibiru.sample.eventBusSample");
		this.getAdapted().setValue(titleText);
		title.setValue(titleText);
		this.getAdapted().addComponent(title);

		this.messageInput = viewFactory.buildTextField(String.class);
		this.messageInput.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.eventbus.message"));
		this.messageInput
				.setValue(messageSource
						.getMessage("ar.com.oxen.nibiru.sample.eventbus.defaultMessage"));
		this.getAdapted().addComponent(this.messageInput);

		this.messageOutput = viewFactory.buildLabel(String.class);
		this.getAdapted().addComponent(this.messageOutput);

		this.sendMessage = viewFactory.buildButton();
		this.sendMessage.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.eventbus.sendMessage"));
		this.getAdapted().addComponent(this.sendMessage);
	}

	@Override
	public HasValue<String> getMessageInput() {
		return this.messageInput;
	}

	@Override
	public HasValue<String> getMessageOutput() {
		return this.messageOutput;
	}

	@Override
	public HasClickHandler getSendMessage() {
		return this.sendMessage;
	}

	@Override
	public HasCloseHandler getClose() {
		return this.getAdapted();
	}

}
