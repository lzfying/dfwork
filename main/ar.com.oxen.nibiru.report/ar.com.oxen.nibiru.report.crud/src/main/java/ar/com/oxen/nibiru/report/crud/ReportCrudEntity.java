package ar.com.oxen.nibiru.report.crud;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import static ar.com.oxen.nibiru.crud.manager.api.CrudField.FormInfo.GENERAL_TAB;
import ar.com.oxen.nibiru.crud.manager.api.WidgetType;
import ar.com.oxen.nibiru.crud.utils.SimpleCrudField;
import ar.com.oxen.nibiru.report.api.Report;
import ar.com.oxen.nibiru.report.api.Report.ParameterDefinition;

class ReportCrudEntity implements CrudEntity<Report> {
	private Report report;
	private String format;
	private Map<String, Object> parameters;

	public ReportCrudEntity(Report report) {
		super();
		this.report = report;
		this.parameters = new HashMap<String, Object>();
	}

	@Override
	public Object getId() {
		return report;
	}

	@Override
	public List<CrudField> getFormFields() {
		List<CrudField> formFields = new LinkedList<CrudField>();

		/* Format field */
		formFields.add(new SimpleCrudField(
				ReportCrudManager.REPORT_FORMAT_FIELD, String.class, null,
				new SimpleCrudField.SimpleFormInfo(WidgetType.COMBO_BOX, false,
						0, GENERAL_TAB, null)));

		/* Parameter fields */
		for (Report.ParameterDefinition paramDef : this.report
				.getParameterDefinitions()) {
			formFields.add(new ParameterDefinitionAdapter(paramDef));
		}

		return formFields;
	}

	@Override
	public Object getValue(CrudField field) {
		return this.getValue(field.getName());
	}

	@Override
	public Object getValue(String fieldName) {
		if (ReportCrudManager.REPORT_NAME_FIELD.equals(fieldName)) {
			return this.report.getName();
		} else if (ReportCrudManager.REPORT_FORMAT_FIELD.equals(fieldName)) {
			return this.format;
		} else {
			return this.parameters.get(fieldName);
		}
	}

	@Override
	public void setValue(CrudField field, Object value) {
		this.setValue(field.getName(), value);
	}

	@Override
	public void setValue(String fieldName, Object value) {
		if (ReportCrudManager.REPORT_NAME_FIELD.equals(fieldName)) {
			throw new IllegalArgumentException("Report name can't be modified "
					+ fieldName);
		} else if (ReportCrudManager.REPORT_FORMAT_FIELD.equals(fieldName)) {
			this.format = (String) value;
		} else {
			this.parameters.put(fieldName, value);
		}
	}

	@Override
	public Report getEntity() {
		return report;
	}

	@Override
	public String getEntityTypeName() {
		return Report.class.getName();
	}

	@Override
	public Iterable<?> getAvailableValues(CrudField field) {
		return this.getAvailableValues(field.getName());
	}

	@Override
	public Iterable<?> getAvailableValues(String fieldName) {
		if (ReportCrudManager.REPORT_FORMAT_FIELD.equals(fieldName)) {
			return this.report.getFormats();
		} else {
			throw new IllegalArgumentException(
					"Field with no available values: " + fieldName);
		}
	}

	private static class ParameterDefinitionAdapter implements CrudField {
		private Report.ParameterDefinition paramDef;

		public ParameterDefinitionAdapter(ParameterDefinition paramDef) {
			super();
			this.paramDef = paramDef;
		}

		@Override
		public String getName() {
			return this.paramDef.getName();
		}

		@Override
		public Class<?> getType() {
			return this.paramDef.getType();
		}

		@Override
		public ListInfo getListInfo() {
			return null;
		}

		@Override
		public FormInfo getFormInfo() {
			return new SimpleCrudField.SimpleFormInfo(WidgetType.TEXT_FIELD,
					false, 9999, GENERAL_TAB, null);
		}
	}
}