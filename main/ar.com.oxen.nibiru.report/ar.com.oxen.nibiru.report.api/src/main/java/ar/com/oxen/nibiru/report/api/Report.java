package ar.com.oxen.nibiru.report.api;

import java.io.InputStream;
import java.util.Map;

public interface Report {
	String EXTENSION_POINT_NAME = "ar.com.oxen.nibiru.reports";

	String getName();

	Iterable<String> getFormats();

	Iterable<ParameterDefinition> getParameterDefinitions();

	InputStream render(String format, Map<String, Object> parameters);

	interface ParameterDefinition {
		String getName();

		Class<?> getType();
	}
}
