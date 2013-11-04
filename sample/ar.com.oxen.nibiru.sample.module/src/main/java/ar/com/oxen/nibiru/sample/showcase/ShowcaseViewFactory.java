package ar.com.oxen.nibiru.sample.showcase;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.sample.conversation.ConversationSamplePresenter;
import ar.com.oxen.nibiru.sample.conversation.ConversationSampleView;
import ar.com.oxen.nibiru.sample.eventbus.EventBusSamplePresenter;
import ar.com.oxen.nibiru.sample.eventbus.EventBusSampleView;
import ar.com.oxen.nibiru.sample.mail.MailSamplePresenter;
import ar.com.oxen.nibiru.sample.mail.MailSampleView;
import ar.com.oxen.nibiru.sample.session.SessionSamplePresenter;
import ar.com.oxen.nibiru.sample.session.SessionSampleView;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;

public class ShowcaseViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;

	public MailSamplePresenter.Display createMailSampleDisplay() {
		return new MailSampleView(this.viewFactory, this.messageSource);
	}

	public EventBusSamplePresenter.Display createEventBusSampleDisplay() {
		return new EventBusSampleView(this.viewFactory, this.messageSource);
	}

	public SessionSamplePresenter.Display createSessionSampleDisplay() {
		return new SessionSampleView(this.viewFactory, this.messageSource);
	}

	public ConversationSamplePresenter.Display createConversationSampleDisplay() {
		return new ConversationSampleView(this.viewFactory, this.messageSource);
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
