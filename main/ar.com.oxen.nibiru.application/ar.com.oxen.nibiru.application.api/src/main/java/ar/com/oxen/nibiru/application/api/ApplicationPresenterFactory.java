package ar.com.oxen.nibiru.application.api;

import ar.com.oxen.nibiru.application.api.about.AboutView;
import ar.com.oxen.nibiru.application.api.main.MainView;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

/**
 * Presenter factory for common application functionality.
 */
public interface ApplicationPresenterFactory {
	/**
	 * Builds the presenter for main window.
	 * 
	 * @return The presenter
	 */
	Presenter<MainView> buildMainPresenter();

	/**
	 * Builds the presenter for about window.
	 * 
	 * @return The presenter
	 */
	Presenter<AboutView> buildAboutPresenter();
}
