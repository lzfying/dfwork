package ar.com.oxen.nibiru.sample.conversation;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class ConversationSampleView extends AbstractWindowViewAdapter<Window>
		implements ConversationSamplePresenter.Display {
	private TextField<String> input;
	private Button write;
	private Button writeAccessor;
	private TextField<String> output;
	private Button read;
	private Button readAccessor;

	public ConversationSampleView(ViewFactory viewFactory,
			MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);
		Label<String> title = viewFactory.buildLabel(String.class);

		String titleText = messageSource
				.getMessage("ar.com.oxen.nibiru.sample.conversationSample");
		this.getAdapted().setValue(titleText);
		title.setValue(titleText);
		this.getAdapted().addComponent(title);

		this.input = viewFactory.buildTextField(String.class);
		this.input.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.input"));
		this.input
				.setValue(messageSource
						.getMessage("ar.com.oxen.nibiru.sample.conversation.defaultInput"));
		this.getAdapted().addComponent(this.input);

		this.write = viewFactory.buildButton();
		this.write.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.conversation.write"));
		this.getAdapted().addComponent(this.write);

		this.writeAccessor = viewFactory.buildButton();
		this.writeAccessor
				.setValue(messageSource
						.getMessage("ar.com.oxen.nibiru.sample.conversation.writeAccessor"));
		this.getAdapted().addComponent(this.writeAccessor);

		this.output = viewFactory.buildTextField(String.class);
		this.output.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.output"));
		this.getAdapted().addComponent(this.output);

		this.read = viewFactory.buildButton();
		this.read.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.conversation.read"));
		this.getAdapted().addComponent(this.read);

		this.readAccessor = viewFactory.buildButton();
		this.readAccessor
				.setValue(messageSource
						.getMessage("ar.com.oxen.nibiru.sample.conversation.readAccessor"));
		this.getAdapted().addComponent(this.readAccessor);
	}

	@Override
	public HasValue<String> getInput() {
		return this.input;
	}

	@Override
	public HasClickHandler getRead() {
		return this.read;
	}

	@Override
	public HasClickHandler getReadAccessor() {
		return this.readAccessor;
	}

	@Override
	public HasValue<String> getOutput() {
		return this.output;
	}

	@Override
	public HasClickHandler getWrite() {
		return this.write;
	}

	@Override
	public HasClickHandler getWriteAccessor() {
		return this.writeAccessor;
	}
}
