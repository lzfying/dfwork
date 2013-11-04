package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class ButtonAdapter extends AbstractComponentAdapter<Button> implements
		ar.com.oxen.nibiru.ui.api.view.Button {
	public ButtonAdapter(Button adapted) {
		super(adapted);
	}

	@Override
	public void setClickHandler(final ClickHandler clickHandler) {
		this.getAdapted().addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 2842312967671169360L;

			@Override
			public void buttonClick(ClickEvent event) {
				clickHandler.onClick();
			}
		});

	}

	@Override
	public void setValue(String value) {
		this.getAdapted().setCaption(value);
	}

	@Override
	public String getValue() {
		return this.getAdapted().getCaption();
	}
}
