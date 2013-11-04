package ar.com.oxen.nibiru.report.module.ui.generic;

import java.io.InputStream;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.report.module.ui.ReportView;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class GenericReportPresenter extends AbstractPresenter<ReportView> {
	private InputStream data;
	private String format;

	public GenericReportPresenter(EventBus eventBus, InputStream data, String format) {
		super(eventBus);
		this.data = data;
		this.format = format;
	}

	@Override
	public void go() {
		this.getView().showReport(this.data, this.format);
		this.configureClose(this.getView());
	}
}
