package ar.com.oxen.nibiru.ui.vaadin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class NibiruApplicationServlet extends AbstractApplicationServlet {
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -4897206489141799154L;

	private ApplicationAccessor applicationAccessor;
	
	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		return this.applicationAccessor.createApplication();
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return Application.class;
	}

	public void setApplicationAccessor(ApplicationAccessor applicationAccessor) {
		this.applicationAccessor = applicationAccessor;
	}
}
