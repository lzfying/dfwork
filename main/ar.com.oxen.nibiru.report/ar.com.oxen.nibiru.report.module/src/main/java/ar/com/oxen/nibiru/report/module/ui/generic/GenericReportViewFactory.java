package ar.com.oxen.nibiru.report.module.ui.generic;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.report.module.ui.ReportView;
import ar.com.oxen.nibiru.report.module.ui.ReportViewFactory;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;

public class GenericReportViewFactory implements ReportViewFactory {
	private MessageSource messageSource;
	private ViewFactory viewFactory;

	@Override
	public ReportView buildReportView() {
		return new GenericReportView(this.viewFactory, this.messageSource);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

}
