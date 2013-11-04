package ar.com.oxen.nibiru.report.module.ui;

import java.io.InputStream;

import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.View;

public interface ReportView extends View, HasCloseWidget {
	void showReport(InputStream data, String format);

}
