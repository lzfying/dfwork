package ar.com.oxen.nibiru.crud.ui.generic.view;

import java.util.Locale;

import ar.com.oxen.nibiru.crud.ui.api.CrudViewFactory;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.dialog.DialogBuilder;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public abstract class AbstractCrudView extends
		AbstractWindowViewAdapter<Window> {
	private Locale locale;

	public AbstractCrudView(Window adapted, ViewFactory viewFactory,
			MessageSource messageSource, LocaleHolder localeHolder) {
		super(adapted, viewFactory, messageSource);
		this.locale = localeHolder.getLocale();
	}

	protected String i18nAction(String action) {
		return this.getMessage(CrudViewFactory.I18N_ACTION_PREFIX + action);
	}

	protected Button createActionButton(String label,
			boolean requiresConfirmation, ClickHandler clickHandler) {
		Button button = this.getViewFactory().buildButton();
		String action = this.i18nAction(label);
		button.setValue(action);
		button.setClickHandler(this.addConfirmation(action,
				requiresConfirmation, clickHandler));
		return button;
	}

	protected ClickHandler addConfirmation(final String action,
			boolean requiresConfirmation, final ClickHandler clickHandler) {
		if (requiresConfirmation) {
			return new ClickHandler() {

				@Override
				public void onClick() {
					new DialogBuilder(getViewFactory())
							.title(getMessage("ar.com.oxen.nibiru.app.confirmation"))
							.message(
									getMessage(
											"ar.com.oxen.nibiru.app.areYouSure",
											action))
							.button(getMessage("ar.com.oxen.nibiru.app.ok"),
									clickHandler)
							.button(getMessage("ar.com.oxen.nibiru.app.cancel"))
							.build().show();
				}
			};
		} else {
			return clickHandler;
		}
	}

	protected String getMessage(String key, Object... params) {
		return this.getMessageSource().getMessage(key, locale, params);
	}
}
