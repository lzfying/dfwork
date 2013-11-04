package ar.com.oxen.nibiru.crud.ui.generic.view;

import ar.com.oxen.nibiru.crud.ui.api.CrudViewFactory;
import ar.com.oxen.nibiru.crud.ui.api.form.CrudFormView;
import ar.com.oxen.nibiru.crud.ui.api.list.CrudListView;
import ar.com.oxen.nibiru.crud.ui.generic.view.form.GenericCrudFormView;
import ar.com.oxen.nibiru.crud.ui.generic.view.list.GenericCrudListView;
import ar.com.oxen.nibiru.i18n.api.LocaleHolder;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;

public class GenericCrudViewFactory implements CrudViewFactory {
	private ViewFactory viewFactory;
	private MessageSource messageSource;
	private LocaleHolder localeHolder;

	@Override
	public CrudListView buildListView() {
		return new GenericCrudListView(this.viewFactory, this.messageSource, this.localeHolder);
	}

	@Override
	public CrudFormView buildFormView() {
		return new GenericCrudFormView(this.viewFactory, this.messageSource, this.localeHolder);
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setLocaleHolder(LocaleHolder localeHolder) {
		this.localeHolder = localeHolder;
	}
}
