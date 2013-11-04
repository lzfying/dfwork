package ar.com.oxen.nibiru.http.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for accessing the current HTTP session.
 */
public class SessionHolder {
	private static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<HttpServletRequest>();
	private static final String SESSION_MUTEX_ATTRIBUTE = HttpSession.class
			.getName() + ".MUTEX";

	public static HttpSession getSession() {
		HttpServletRequest request = threadLocal.get();
		HttpSession session = request.getSession(false);
		if (session == null) {
			synchronized (request) {
				session = request.getSession(false);
				if (session == null) {
					session = request.getSession(true);
					session.setAttribute(SESSION_MUTEX_ATTRIBUTE, new Mutex());
				}
			}
		}
		return session;
	}

	public static boolean sessionExists() {
		return threadLocal.get().getSession(false) != null;
	}

	public static Object getMutex() {
		return getSession().getAttribute(SESSION_MUTEX_ATTRIBUTE);
	}

	static void setRequest(HttpServletRequest request) {
		threadLocal.set(request);
	}

	private static class Mutex implements Serializable {
		private static final long serialVersionUID = -6633349976931115539L;
	}
}
