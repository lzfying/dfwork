package ar.com.oxen.nibiru.report.module.ui;

import java.io.InputStream;

import ar.com.oxen.nibiru.ui.api.mvp.Presenter;

public interface ReportPresenterFactory {
	Presenter<ReportView> buildReportPresenter(InputStream data, String format);
}
