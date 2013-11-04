package ar.com.oxen.nibiru.sample.session;

import ar.com.oxen.nibiru.sample.session.SessionSamplePresenter.Display;
import ar.com.oxen.nibiru.session.api.Session;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class SessionSamplePresenter extends AbstractPresenter<Display> {
	public interface Display extends View {
		HasValue<String> getInput();

		HasClickHandler getWrite();

		HasValue<String> getOutput();

		HasClickHandler getRead();
	}

	private final static String SESSION_KEY = "ar.com.oxen.nibiru.sample.sessionKey";

	private Session session;

	public SessionSamplePresenter(Session session) {
		super(null);
		this.session = session;
	}

	@Override
	public void go() {
		this.getView().getWrite().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				writeSession();
			}
		});

		this.getView().getRead().setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				readSession();
			}
		});
	}

	private void writeSession() {
		String value = getView().getInput().getValue();
		session.put(SESSION_KEY, value);
	}

	private void readSession() {
		String value = session.get(SESSION_KEY);
		getView().getOutput().setValue(value);
	}
}
