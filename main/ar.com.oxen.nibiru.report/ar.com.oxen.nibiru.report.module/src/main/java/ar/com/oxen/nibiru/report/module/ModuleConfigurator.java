package ar.com.oxen.nibiru.report.module;

import ar.com.oxen.commons.eventbus.api.EventHandlerMethod;
import ar.com.oxen.nibiru.crud.utils.AbstractCrudModuleConfigurator;
import ar.com.oxen.nibiru.report.api.Report;
import ar.com.oxen.nibiru.report.crud.ReportCrudActionExtension;
import ar.com.oxen.nibiru.report.crud.ReportCrudManager;
import ar.com.oxen.nibiru.report.crud.ReportExecutedEvent;
import ar.com.oxen.nibiru.report.module.ui.ReportPresenterFactory;
import ar.com.oxen.nibiru.report.module.ui.ReportViewFactory;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleSubMenuExtension;
import ar.com.oxen.nibiru.validation.api.Validator;
import ar.com.oxen.nibiru.validation.generic.NotEmptyValidator;

public class ModuleConfigurator extends AbstractCrudModuleConfigurator {
	private static final String MENU_EXTENSION = "ar.com.oxen.nibiru.menu.reports";
	private ReportPresenterFactory reportPresenterFactory;
	private ReportViewFactory reportViewFactory;

	@Override
	protected void configure() {
		this.registerExtension(new SimpleSubMenuExtension("reports",
				MENU_EXTENSION, 1), "ar.com.oxen.nibiru.menu",
				SubMenuExtension.class);

		this.addCrudWithMenu("reports.manage", MENU_EXTENSION,
				new ReportCrudManager(this.getExtensionPointManager()),
				new ReportCrudActionExtension(this.getEventBus()));
		
		this.registerExtension(new NotEmptyValidator(), Report.class.getName()
				+ "." + ReportCrudManager.REPORT_FORMAT_FIELD, Validator.class);
	}

	@EventHandlerMethod
	public void onReportExecutedEvent(ReportExecutedEvent event) {
		this.activate(
				this.reportViewFactory.buildReportView(),
				this.reportPresenterFactory.buildReportPresenter(
						event.getData(), event.getFormat()));
	}

	public void setReportPresenterFactory(
			ReportPresenterFactory reportPresenterFactory) {
		this.reportPresenterFactory = reportPresenterFactory;
	}

	public void setReportViewFactory(ReportViewFactory reportViewFactory) {
		this.reportViewFactory = reportViewFactory;
	}
}
