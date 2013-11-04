package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import com.vaadin.ui.CheckBox;

public class CheckBoxAdapter extends AbstractFieldAdapter<Boolean, CheckBox>
		implements ar.com.oxen.nibiru.ui.api.view.CheckBox {
	public CheckBoxAdapter(CheckBox adapted) {
		super(adapted);
	}

	@Override
	public void setValue(Boolean value) {
		this.getAdapted().getPropertyDataSource().setValue(value);
	}

	@Override
	public Boolean getValue() {
		return (Boolean) this.getAdapted().getPropertyDataSource().getValue();
	}
}
