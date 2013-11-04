package ar.com.oxen.nibiru.session.http;

import java.io.Serializable;

import ar.com.oxen.nibiru.http.utils.SessionHolder;
import ar.com.oxen.nibiru.session.api.Session;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class HttpSession implements Session {
	private static final String DESTRUCTION_CALLBACK_NAME_PREFIX = HttpSession.class
			.getName() + ".DESTRUCTION_CALLBACK.";

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String name) {
		return (T) SessionHolder.getSession().getAttribute(name);
	}

	@Override
	public void put(String name, Object object) {
		SessionHolder.getSession().setAttribute(name, object);
	}

	@Override
	public void remove(String name) {
		SessionHolder.getSession().removeAttribute(name);
	}

	@Override
	public String getId() {
		return SessionHolder.getSession().getId();
	}

	@Override
	public Object getMutex() {
		return SessionHolder.getMutex();
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		SessionHolder.getSession().setAttribute(
				DESTRUCTION_CALLBACK_NAME_PREFIX + name,
				new DestructionCallbackBindingListener(callback));
	}

	@Override
	public boolean isValid() {
		return SessionHolder.sessionExists();
	}

	private static class DestructionCallbackBindingListener implements
			HttpSessionBindingListener, Serializable {
		private static final long serialVersionUID = -6555086284897255312L;
		private final Runnable destructionCallback;

		/**
		 * Create a new DestructionCallbackBindingListener for the given
		 * callback.
		 * 
		 * @param destructionCallback
		 *            the Runnable to execute when this listener object gets
		 *            unbound from the session
		 */
		public DestructionCallbackBindingListener(Runnable destructionCallback) {
			this.destructionCallback = destructionCallback;
		}

		public void valueBound(HttpSessionBindingEvent event) {
		}

		public void valueUnbound(HttpSessionBindingEvent event) {
			this.destructionCallback.run();
		}
	}
}
