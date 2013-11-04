package ar.com.oxen.nibiru.sample.showcase;

import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;
import ar.com.oxen.nibiru.sample.conversation.ConversationSampleEvent;
import ar.com.oxen.nibiru.sample.eventbus.EventBusSampleEvent;
import ar.com.oxen.nibiru.sample.mail.MailSampleEvent;
import ar.com.oxen.nibiru.sample.session.SessionSampleEvent;

public class ShowcaseModuleConfigurator
		extends
		AbstractModuleConfigurator<ShowcaseViewFactory, ShowcasePresenterFactory> {
	private static final String MENU_EXTENSION = "ar.com.oxen.nibiru.menu.sample.showcase";

	@Override
	protected void configure() {
		this.registerSubMenu("sample.showcase", MENU_EXTENSION,
				"ar.com.oxen.nibiru.menu", 3);

		this.registerMenu("sample.mail", MENU_EXTENSION, MailSampleEvent.class,
				null, null);

		this.registerMenu("sample.eventbus", MENU_EXTENSION,
				EventBusSampleEvent.class, null, null);

		this.registerMenu("sample.session", MENU_EXTENSION,
				SessionSampleEvent.class, null, null);

		this.registerMenu("sample.conversation", MENU_EXTENSION,
				ConversationSampleEvent.class, null, null);
	}

	@EventHandlerMethod
	public void onMailSample(MailSampleEvent event) {
		this.activate(this.getViewFactory().createMailSampleDisplay(), this
				.getPresenterFactory().createMailSamplePresenter());
	}

	@EventHandlerMethod
	public void onEventBusSample(EventBusSampleEvent event) {
		this.activate(this.getViewFactory().createEventBusSampleDisplay(), this
				.getPresenterFactory().createEventBusSamplePresenter());
	}

	@EventHandlerMethod
	public void onSessionSample(SessionSampleEvent event) {
		this.activate(this.getViewFactory().createSessionSampleDisplay(), this
				.getPresenterFactory().createSessionSamplePresenter());
	}

	@EventHandlerMethod
	public void onConversationSample(ConversationSampleEvent event) {
		this.activate(this.getViewFactory().createConversationSampleDisplay(),
				this.getPresenterFactory().createConversationSamplePresenter());
	}
}
