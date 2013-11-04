package ar.com.oxen.nibiru.report.birt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.engine.api.ReportEngine;

import ar.com.oxen.nibiru.report.api.Report;

public class BirtReport implements Report {
	private IReportEngine engine;
	private IReportRunnable design;

	public BirtReport(String file) {
		super();
		try {
			engine = new ReportEngine(new EngineConfig());
			this.design = engine.openReportDesign(this
					.getReporFileInputStream(file));
		} catch (EngineException e) {
			throw new BirtReportException(e);
		}
	}

	@Override
	public String getName() {
		return this.design.getDesignInstance().getDisplayName();
	}

	@Override
	public Iterable<String> getFormats() {
		return Arrays.asList(new String[] { "pdf", "html" });
	}

	@Override
	public Iterable<ParameterDefinition> getParameterDefinitions() {
		@SuppressWarnings("unchecked")
		Collection<IParameterDefn> parameterDefns = engine
				.createGetParameterDefinitionTask(design).getParameterDefns(
						false);
		List<ParameterDefinition> parameters = new ArrayList<Report.ParameterDefinition>(
				parameterDefns.size());

		for (IParameterDefn parameterDefn : parameterDefns) {
			parameters.add(new IParameterDefnAdapter(parameterDefn));
		}

		return parameters;
	}

	@Override
	public InputStream render(final String format,
			final Map<String, Object> parameters) {
		try {
			final PipedOutputStream output = new PipedOutputStream();
			InputStream input = new PipedInputStream(output);

			final ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

			new Thread(new Runnable() {
				@Override
				public void run() {
					render(format, parameters, output, classLoader);
				}
			}).start();

			return input;
		} catch (IOException e) {
			throw new BirtReportException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void render(String format, Map<String, Object> parameters,
			OutputStream output, ClassLoader classLoader) {
		try {
			/* Create task to run and render the report */
			IRunAndRenderTask task = design.getReportEngine()
					.createRunAndRenderTask(design);

			/* Set parent classloader for engine */
			task.getAppContext().put(
					EngineConstants.APPCONTEXT_CLASSLOADER_KEY, classLoader);

			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				task.setParameterValue(entry.getKey(), entry.getValue());
			}

			final IRenderOption options = new RenderOption();
			options.setOutputFormat(format);

			options.setOutputStream(output);

			if (options.getOutputFormat().equalsIgnoreCase("html")) {
				final HTMLRenderOption htmlOptions = new HTMLRenderOption(
						options);
				htmlOptions.setImageDirectory("img");
				htmlOptions.setHtmlPagination(false);
				htmlOptions.setHtmlRtLFlag(false);
				htmlOptions.setEmbeddable(false);
				htmlOptions.setSupportedImageFormats("PNG");
			} else if (options.getOutputFormat().equalsIgnoreCase("pdf")) {
				final PDFRenderOption pdfOptions = new PDFRenderOption(options);
				pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
						IPDFRenderOption.FIT_TO_PAGE_SIZE);
				pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
						IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
			}

			task.setRenderOption(options);

			// run and render report
			task.run();

			task.close();

			output.flush();
			output.close();
		} catch (EngineException e) {
			throw new BirtReportException(e);
		} catch (IOException e) {
			throw new BirtReportException(e);
		}
	}

	private InputStream getReporFileInputStream(String file) {
		InputStream reportInputStream = this.getClass().getResourceAsStream(
				file);
		if (reportInputStream == null) {
			reportInputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(file);
		}
		if (reportInputStream == null) {
			throw new IllegalArgumentException("Invalid report file: " + file);
		}

		return reportInputStream;
	}

	private static class IParameterDefnAdapter implements ParameterDefinition {
		private IParameterDefn parameterDefn;

		public IParameterDefnAdapter(IParameterDefn parameterDefn) {
			super();
			this.parameterDefn = parameterDefn;
		}

		@Override
		public String getName() {
			return this.parameterDefn.getName();
		}

		@Override
		public Class<?> getType() {
			switch (this.parameterDefn.getDataType()) {
			case IParameterDefn.TYPE_BOOLEAN:
				return Boolean.class;
			case IParameterDefn.TYPE_DATE:
			case IParameterDefn.TYPE_TIME:
			case IParameterDefn.TYPE_DATE_TIME:
				return Date.class;
			case IParameterDefn.TYPE_DECIMAL:
				return Double.class;
			case IParameterDefn.TYPE_FLOAT:
				return Float.class;
			case IParameterDefn.TYPE_STRING:
				return String.class;
			default:
				throw new IllegalStateException("Invalid parameter type: "
						+ this.parameterDefn.getDataType());
			}
		}
	}
}
