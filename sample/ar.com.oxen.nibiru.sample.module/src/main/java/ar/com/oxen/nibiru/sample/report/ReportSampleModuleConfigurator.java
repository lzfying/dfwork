package ar.com.oxen.nibiru.sample.report;

import javax.sql.DataSource;

import ar.com.oxen.nibiru.module.utils.AbstractModuleConfigurator;
import ar.com.oxen.nibiru.report.api.Report;
import ar.com.oxen.nibiru.report.jasper.JasperReport;

public class ReportSampleModuleConfigurator extends
		AbstractModuleConfigurator<Object, Object> {
	private DataSource dataSource;

	@Override
	protected void configure() {
		registerExtension(
				new JasperReport(this.getClass().getResourceAsStream(
						"/ar/com/oxen/nibiru/sample/report/myReport.jrxml"),
						this.dataSource), Report.EXTENSION_POINT_NAME,
				Report.class);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
