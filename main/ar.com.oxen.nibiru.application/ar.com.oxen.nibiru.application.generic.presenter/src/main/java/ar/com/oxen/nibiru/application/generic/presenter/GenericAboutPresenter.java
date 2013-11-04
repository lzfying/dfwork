package ar.com.oxen.nibiru.application.generic.presenter;

import ar.com.oxen.nibiru.application.api.about.AboutView;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class GenericAboutPresenter extends AbstractPresenter<AboutView> {

	public GenericAboutPresenter() {
		super(null);
	}

	@Override
	public void go() {
		this.configureClose(this.getView());
	}
}
