package ar.com.oxen.nibiru.report.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import ar.com.oxen.nibiru.report.api.Report;

public class JasperReport implements Report {
	private net.sf.jasperreports.engine.JasperReport report;
	private DataSource dataSource;

	public JasperReport(InputStream reportInputStream, DataSource dataSource) {
		super();
		try {
			this.report = JasperCompileManager.compileReport(reportInputStream);
			this.dataSource = dataSource;
		} catch (JRException e) {
			throw new JasperReportException(e);
		}
	}

	@Override
	public String getName() {
		return this.report.getName();
	}

	@Override
	public Iterable<String> getFormats() {
		return Arrays.asList(new String[] { "pdf" });
	}

	@Override
	public Iterable<ParameterDefinition> getParameterDefinitions() {
		JRParameter reportParameters[] = this.report.getParameters();
		List<ParameterDefinition> parameterDefinitions = new ArrayList<Report.ParameterDefinition>(
				reportParameters.length);

		for (JRParameter reportParameter : reportParameters) {
			if (reportParameter.isForPrompting()
					&& !reportParameter.isSystemDefined()) {
				parameterDefinitions
						.add(new JRParameterAdapter(reportParameter));
			}
		}

		return parameterDefinitions;
	}

	@Override
	public InputStream render(final String format,
			final Map<String, Object> parameters) {
		try {
			final PipedOutputStream output = new PipedOutputStream();
			InputStream input = new PipedInputStream(output);

			new Thread(new Runnable() {
				@Override
				public void run() {
					render(format, parameters, output);
				}
			}).start();

			return input;
		} catch (IOException e) {
			throw new JasperReportException(e);
		}
	}

	private void render(String format, Map<String, Object> parameters,
			OutputStream output) {
		Connection connection = null;
		try {

			connection = this.dataSource.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(this.report,
					parameters, connection);

			if (format.equals("pdf")) {
				JasperExportManager
						.exportReportToPdfStream(jasperPrint, output);
			} else {
				throw new IllegalArgumentException("Invalid report format: "
						+ format);
			}
		} catch (SQLException e) {
			throw new JasperReportException(e);
		} catch (JRException e) {
			throw new JasperReportException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new JasperReportException(e);
				}
			}
		}
	}

	private static class JRParameterAdapter implements ParameterDefinition {
		private JRParameter parameter;

		public JRParameterAdapter(JRParameter parameter) {
			super();
			this.parameter = parameter;
		}

		@Override
		public String getName() {
			return this.parameter.getName();
		}

		@Override
		public Class<?> getType() {
			return this.parameter.getValueClass();
		}
	}
}
