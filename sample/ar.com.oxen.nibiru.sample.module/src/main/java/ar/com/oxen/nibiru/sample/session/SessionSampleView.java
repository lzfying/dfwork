package ar.com.oxen.nibiru.sample.session;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class SessionSampleView extends AbstractWindowViewAdapter<Window>
		implements SessionSamplePresenter.Display {
	private TextField<String> input;
	private Button write;
	private TextField<String> output;
	private Button read;

	public SessionSampleView(ViewFactory viewFactory,
			MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);
		Label<String> title = viewFactory.buildLabel(String.class);

		String titleText = messageSource
				.getMessage("ar.com.oxen.nibiru.sample.sessionSample");
		this.getAdapted().setValue(titleText);
		title.setValue(titleText);
		this.getAdapted().addComponent(title);

		this.input = viewFactory.buildTextField(String.class);
		this.input.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.input"));
		this.input.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.session.defaultInput"));
		this.getAdapted().addComponent(this.input);

		this.write = viewFactory.buildButton();
		this.write.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.session.write"));
		this.getAdapted().addComponent(this.write);

		this.output = viewFactory.buildTextField(String.class);
		this.output.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.output"));
		this.getAdapted().addComponent(this.output);

		this.read = viewFactory.buildButton();
		this.read.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.session.read"));
		this.getAdapted().addComponent(this.read);
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
	public HasValue<String> getOutput() {
		return this.output;
	}

	@Override
	public HasClickHandler getWrite() {
		return this.write;
	}
}
