package ar.com.oxen.nibiru.sample.conversation;

import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationAccessor;
import ar.com.oxen.nibiru.conversation.api.ConversationCallback;
import ar.com.oxen.nibiru.conversation.api.ConversationFactory;
import ar.com.oxen.nibiru.sample.conversation.ConversationSamplePresenter.Display;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class ConversationSamplePresenter extends AbstractPresenter<Display> {
	public interface Display extends View {
		HasValue<String> getInput();

		HasClickHandler getWrite();

		HasClickHandler getWriteAccessor();

		HasValue<String> getOutput();

		HasClickHandler getRead();

		HasClickHandler getReadAccessor();
	}

	private final static String CONVERSATION_KEY = "ar.com.oxen.nibiru.sample.conversationKey";

	private Conversation conversation;
	private ConversationAccessor conversationAccessor;

	public ConversationSamplePresenter(ConversationFactory conversationFactory,
			ConversationAccessor conversationAccessor) {
		super(null);
		this.conversation = conversationFactory.buildConversation();
		this.conversationAccessor = conversationAccessor;
	}

	@Override
	public void go() {
		this.getView().getWrite().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				writeConversation();
			}
		});

		this.getView().getWriteAccessor().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				writeConversationAccessor();
			}
		});

		this.getView().getRead().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				readConversation();
			}
		});

		this.getView().getReadAccessor().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				readConversationAccessor();
			}
		});
	}
	
	private void writeConversation() {
		String value = this.getView().getInput().getValue();
		this.conversation.put(CONVERSATION_KEY, value);
	}
	
	private void writeConversationAccessor() {
		this.conversation.execute(new ConversationCallback<Void>() {
			@Override
			public Void doInConversation(Conversation conversation)
					throws Exception {
				String value = getView().getInput().getValue();
				conversationAccessor.getCurrentConversation().put(CONVERSATION_KEY, value);
				return null;
			}
		});
	}

	private void readConversation() {
		String value = this.conversation.get(CONVERSATION_KEY);
		this.getView().getOutput().setValue(value);
	}
	
	private void readConversationAccessor() {
		this.conversation.execute(new ConversationCallback<Void>() {
			@Override
			public Void doInConversation(Conversation conversation)
					throws Exception {
				String value = conversation.get(CONVERSATION_KEY);
				getView().getOutput().setValue(value);
				return null;
			}
		});
	}
}
