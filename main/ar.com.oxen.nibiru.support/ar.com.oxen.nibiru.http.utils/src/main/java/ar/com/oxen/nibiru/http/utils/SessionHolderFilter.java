package ar.com.oxen.nibiru.http.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter for storing HTTP session into a holder.
 */
public class SessionHolderFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		SessionHolder.setRequest((HttpServletRequest) req);
		chain.doFilter(req, res);
		SessionHolder.setRequest(null);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
