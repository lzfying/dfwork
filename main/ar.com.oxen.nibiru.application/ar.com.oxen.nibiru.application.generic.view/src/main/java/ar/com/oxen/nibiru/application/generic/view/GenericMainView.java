package ar.com.oxen.nibiru.application.generic.view;

import java.util.Locale;

import ar.com.oxen.nibiru.application.api.main.MainView;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.MainWindow;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class GenericMainView extends AbstractWindowViewAdapter<MainWindow>
		implements MainView {
	private static String I18N_MENU_PREFIX = "ar.com.oxen.nibiru.app.menu.";
	private Locale locale;
	private Label<String> userLabel;

	public GenericMainView(ViewFactory viewFactory,
			MessageSource messageSource, Locale locale) {
		super(viewFactory.buildMainWindow(), viewFactory, messageSource);
		this.locale = locale;

		String appName = messageSource
				.getMessage("ar.com.oxen.nibiru.app.name");
		if (appName != null) {
			getAdapted().setValue(appName);

			Label<String> titleLabel = viewFactory.buildLabel(String.class);
			titleLabel.setValue(messageSource.getMessage(
					"ar.com.oxen.nibiru.app.info", appName));
			getAdapted().getInfoContainer().addComponent(titleLabel);
			
			/* Set main window title */
			getAdapted().setValue(appName);
		}
		this.userLabel = viewFactory.buildLabel(String.class);
		getAdapted().getInfoContainer().addComponent(this.userLabel);
	}

	@Override
	public HasMenuItems getMainMenu() {
		return this.getAdapted();
	}

	@Override
	public MenuItem addMenuItem(String name, HasMenuItems parent, int position) {
		MenuItem item = parent.addMenuItem(this.getMessageSource().getMessage(
				I18N_MENU_PREFIX + name, locale), position);
		return item;
	}

	@Override
	public void setUserName(String userName) {
		this.userLabel.setValue(getMessageSource().getMessage(
				"ar.com.oxen.nibiru.app.userName", userName));
	}
}
