package ar.com.oxen.nibiru.report.crud;

import java.io.InputStream;

import ar.com.oxen.nibiru.report.api.Report;

public class ReportExecutedEvent {
	private Report report;
	private String format;
	private InputStream data;

	public ReportExecutedEvent(Report report, String format, InputStream data) {
		super();
		this.report = report;
		this.format = format;
		this.data = data;
	}

	public Report getReport() {
		return report;
	}

	public String getFormat() {
		return format;
	}

	public InputStream getData() {
		return data;
	}
}
