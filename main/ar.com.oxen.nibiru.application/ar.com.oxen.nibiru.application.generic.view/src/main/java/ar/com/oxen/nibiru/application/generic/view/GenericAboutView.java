package ar.com.oxen.nibiru.application.generic.view;

import ar.com.oxen.nibiru.application.api.about.AboutView;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class GenericAboutView extends AbstractWindowViewAdapter<Window>
		implements AboutView {
	public GenericAboutView(ViewFactory viewFactory, MessageSource messageSource) {
		super(viewFactory.buildWindow(null), viewFactory, messageSource);
		this.getTitle().setValue(
				messageSource.getMessage("ar.com.oxen.nibiru.app.aboutNibiru"));
		Label<String> label = this.getViewFactory().buildLabel(String.class);
		label.setValue(messageSource
				.getMessage("ar.com.oxen.nibiru.app.aboutNibiruText"));
		this.getAdapted().addComponent(label);
		this.addClose();
	}
}
