package ar.com.oxen.nibiru.report.module.ui.generic;

import java.io.InputStream;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.report.module.ui.ReportView;
import ar.com.oxen.nibiru.ui.api.view.Embedded;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;
import ar.com.oxen.nibiru.ui.utils.view.AbstractWindowViewAdapter;

public class GenericReportView extends AbstractWindowViewAdapter<Window>
		implements ReportView {

	private Embedded report;

	public GenericReportView(ViewFactory viewFactory,
			MessageSource messageSource) {
		super(viewFactory.buildWindow(Window.Style.FORM), viewFactory,
				messageSource);

		this.getAdapted()
				.setValue(
						messageSource
								.getMessage("ar.com.oxen.nibiru.crud.entity.ar.com.oxen.nibiru.report.api.Report"));

		this.report = viewFactory.buildEmbedded();
		this.report.setWidth(600);
		this.report.setHeight(400);
		this.getAdapted().addComponent(this.report);
		this.addClose();
	}

	@Override
	public void showReport(InputStream data, String format) {
		this.report.setData(data, format);
	}
}
