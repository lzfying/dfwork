package ar.com.oxen.nibiru.spring.utils;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DelegatingServletProxy implements Servlet {
	private Servlet servletBean;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletConfig
						.getServletContext());

		this.servletBean = context.getBean(servletConfig.getServletName(),
				Servlet.class);

		this.servletBean.init(servletConfig);
	}

	@Override
	public void destroy() {
		this.servletBean.destroy();
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		this.servletBean.service(request, response);
	}

	@Override
	public ServletConfig getServletConfig() {
		return this.servletBean.getServletConfig();
	}

	@Override
	public String getServletInfo() {
		return this.servletBean.getServletInfo();
	}

}
