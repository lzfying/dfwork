package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.ui.TextField;

public class TimeFieldAdapter extends AbstractFieldAdapter<Date, TextField>
		implements ar.com.oxen.nibiru.ui.api.view.TimeField {
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	private Date lastValidValue;

	public TimeFieldAdapter(final TextField adapted) {
		super(adapted);
		adapted.setNullRepresentation("");
		adapted.addListener(new FieldEvents.BlurListener() {
			private static final long serialVersionUID = 5724001817706605097L;

			@Override
			public void blur(BlurEvent event) {
				try {
					updateValue();
				} catch (ParseException e) {
					adapted.focus();
				}
				setValue(lastValidValue);
			}
		});
	}

	@Override
	public void setValue(Date value) {
		if (value != null) {
			this.getAdapted().getPropertyDataSource().setValue(
					this.dateFormat.format(value));
		} else {
			this.getAdapted().getPropertyDataSource().setValue("");
		}
		this.lastValidValue = value;
	}

	@Override
	public Date getValue() {
		try {
			this.updateValue();
		} catch (ParseException e) {
		}
		return this.lastValidValue;
	}

	private void updateValue() throws ParseException {
		String stringValue = (String) this.getAdapted().getPropertyDataSource()
				.getValue();

		if (stringValue != null) {
			stringValue = stringValue.trim();
		} else {
			stringValue = "";
		}

		if (!stringValue.equals("")) {
			this.lastValidValue = this.dateFormat.parse(stringValue);
		} else {
			this.lastValidValue = null;
		}
	}
}
