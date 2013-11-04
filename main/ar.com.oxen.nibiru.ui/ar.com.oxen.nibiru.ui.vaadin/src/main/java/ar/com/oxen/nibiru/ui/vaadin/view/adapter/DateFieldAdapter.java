package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.util.Date;

import com.vaadin.ui.DateField;

public class DateFieldAdapter extends AbstractFieldAdapter<Date, DateField>
		implements ar.com.oxen.nibiru.ui.api.view.DateField {
	public DateFieldAdapter(DateField adapted) {
		super(adapted);
	}

	@Override
	public void setValue(Date value) {
		this.getAdapted().getPropertyDataSource().setValue(value);
	}

	@Override
	public Date getValue() {
		return (Date) this.getAdapted().getPropertyDataSource().getValue();
	}
}
