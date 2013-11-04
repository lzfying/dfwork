package ar.com.oxen.nibiru.ui.vaadin.api;

import com.vaadin.Application;

/**
 * Interface for accessing Vaadin application from a service. Since
 * {@link Application} is not an interface, it can't be exposed as a service.
 */
public interface ApplicationAccessor {
	Application createApplication();

	Application getApplication();
}
