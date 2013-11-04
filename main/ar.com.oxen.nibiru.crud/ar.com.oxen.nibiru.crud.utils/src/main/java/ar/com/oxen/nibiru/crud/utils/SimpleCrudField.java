package ar.com.oxen.nibiru.crud.utils;

import ar.com.oxen.nibiru.crud.manager.api.CrudField;
import ar.com.oxen.nibiru.crud.manager.api.WidgetType;

public class SimpleCrudField implements CrudField {
	private String name;
	private Class<?> type;
	private CrudField.ListInfo listInfo;
	private CrudField.FormInfo formInfo;

	public SimpleCrudField(String name, Class<?> type, ListInfo listInfo,
			FormInfo formInfo) {
		super();
		this.name = name;
		this.type = type;
		this.listInfo = listInfo;
		this.formInfo = formInfo;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public ListInfo getListInfo() {
		return this.listInfo;
	}

	@Override
	public FormInfo getFormInfo() {
		return this.formInfo;
	}

	public static class SimpleListInfo implements CrudField.ListInfo {
		private int columnWidth;

		public SimpleListInfo(int columnWidth) {
			super();
			this.columnWidth = columnWidth;
		}

		@Override
		public int getColumnWidth() {
			return this.columnWidth;
		}

	}

	public static class SimpleFormInfo implements CrudField.FormInfo {
		private WidgetType widgetType;
		private boolean readonly;
		private int maxLength;
		private String tab;
		private String[] values;

		public SimpleFormInfo(WidgetType widgetType, boolean readonly,
				int maxLength, String tab, String[] values) {
			super();
			this.widgetType = widgetType;
			this.readonly = readonly;
			this.maxLength = maxLength;
			this.tab = tab;
			this.values = values;
		}

		@Override
		public WidgetType getWidgetType() {
			return this.widgetType;
		}

		@Override
		public boolean isReadonly() {
			return this.readonly;
		}

		@Override
		public int getMaxLength() {
			return this.maxLength;
		}

		@Override
		public String getTab() {
			return this.tab;
		}

		@Override
		public String[] getValues() {
			return values;
		}
	}
}