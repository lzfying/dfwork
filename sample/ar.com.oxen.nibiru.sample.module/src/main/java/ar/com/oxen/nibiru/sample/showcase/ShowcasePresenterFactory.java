package ar.com.oxen.nibiru.sample.showcase;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.conversation.api.ConversationAccessor;
import ar.com.oxen.nibiru.conversation.api.ConversationFactory;
import ar.com.oxen.nibiru.mail.api.MailService;
import ar.com.oxen.nibiru.sample.conversation.ConversationSamplePresenter;
import ar.com.oxen.nibiru.sample.eventbus.EventBusSamplePresenter;
import ar.com.oxen.nibiru.sample.mail.MailSamplePresenter;
import ar.com.oxen.nibiru.sample.session.SessionSamplePresenter;
import ar.com.oxen.nibiru.session.api.Session;

public class ShowcasePresenterFactory {
	private EventBus eventBus;
	private MailService mailService;
	private Session session;
	private ConversationFactory conversationFactory;
	private ConversationAccessor conversationAccessor;

	public MailSamplePresenter createMailSamplePresenter() {
		return new MailSamplePresenter(this.mailService);
	}

	public EventBusSamplePresenter createEventBusSamplePresenter() {
		return new EventBusSamplePresenter(this.eventBus);
	}

	public SessionSamplePresenter createSessionSamplePresenter() {
		return new SessionSamplePresenter(this.session);
	}

	public ConversationSamplePresenter createConversationSamplePresenter() {
		return new ConversationSamplePresenter(this.conversationFactory,
				this.conversationAccessor);
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setConversationFactory(ConversationFactory conversationFactory) {
		this.conversationFactory = conversationFactory;
	}

	public void setConversationAccessor(
			ConversationAccessor conversationAccessor) {
		this.conversationAccessor = conversationAccessor;
	}
}
