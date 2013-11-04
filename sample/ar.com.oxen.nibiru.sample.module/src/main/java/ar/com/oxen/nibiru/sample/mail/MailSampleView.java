package ar.com.oxen.nibiru.sample.mail;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.TextArea;
import ar.com.oxen.nibiru.ui.api.view.TextField;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.dialog.DialogBuilder;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class MailSampleView extends AbstractWindowViewAdapter<Window> implements
		MailSamplePresenter.Display {
	private TextField<String> to;
	private TextField<String> subject;
	private TextArea<String> body;
	private Button send;

	public MailSampleView(ViewFactory viewFactory, MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);
		Label<String> title = viewFactory.buildLabel(String.class);

		String titleText = messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mailSample");
		this.getAdapted().setValue(titleText);
		title.setValue(titleText);
		this.getAdapted().addComponent(title);

		this.to = viewFactory.buildTextField(String.class);
		this.to.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.to"));
		this.to.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.defaultTo"));
		this.getAdapted().addComponent(this.to);

		this.subject = viewFactory.buildTextField(String.class);
		this.subject.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.subject"));
		this.subject.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.defaultSubject"));
		this.getAdapted().addComponent(this.subject);

		this.body = viewFactory.buildTextArea(String.class);
		this.body.setCaption(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.body"));
		this.body.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.defaultBody"));
		this.getAdapted().addComponent(this.body);

		this.send = viewFactory.buildButton();
		this.send.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.sample.mail.send"));
		this.getAdapted().addComponent(this.send);
	}

	@Override
	public HasValue<String> getTo() {
		return this.to;
	}

	@Override
	public HasValue<String> getSubject() {
		return this.subject;
	}

	@Override
	public HasValue<String> getBody() {
		return this.body;
	}

	@Override
	public HasClickHandler getSend() {
		return this.send;
	}

	@Override
	public void notifyOk() {
		this.showMessage(this.getMessageSource().getMessage(
				"ar.com.oxen.nibiru.sample.mail.messageSent"));
	}

	@Override
	public void notifyError(Exception error) {
		this.showMessage(error.getLocalizedMessage());
	}

	private void showMessage(String message) {
		new DialogBuilder(this.getViewFactory())
				.title(this.getMessageSource().getMessage(
						"ar.com.oxen.nibiru.sample.mail.result"))
				.message(message)
				.button(this.getMessageSource().getMessage(
						"ar.com.oxen.nibiru.app.ok")).build().show();
	}
}
