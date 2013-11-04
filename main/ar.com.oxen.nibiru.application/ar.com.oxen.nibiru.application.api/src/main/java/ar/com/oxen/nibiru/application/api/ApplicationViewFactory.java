package ar.com.oxen.nibiru.application.api;

import ar.com.oxen.nibiru.application.api.about.AboutView;
import ar.com.oxen.nibiru.application.api.main.MainView;

/**
 * View factory for common application functionality.
 */
public interface ApplicationViewFactory {
	/**
	 * Builds the view for main window.
	 * 
	 * @return The view
	 */
	MainView buildMainView();

	/**
	 * Builds the view for about window.
	 * 
	 * @return The view
	 */
	AboutView buildAboutView();
}
