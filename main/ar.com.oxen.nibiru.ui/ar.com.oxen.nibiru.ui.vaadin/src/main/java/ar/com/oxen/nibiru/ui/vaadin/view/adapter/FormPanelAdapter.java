package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.util.LinkedList;
import java.util.List;

import ar.com.oxen.nibiru.ui.api.view.FormField;
import ar.com.oxen.nibiru.ui.api.view.FormPanel;
import ar.com.oxen.nibiru.ui.utils.view.AbstractAdapter;

import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;

public class FormPanelAdapter extends AbstractComponentAdapter<Form> implements
		FormPanel {
	private List<FormField<?>> fields;

	public FormPanelAdapter(Form form) {
		super(form);
		form.setImmediate(true);
		this.fields = new LinkedList<FormField<?>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addComponent(ar.com.oxen.nibiru.ui.api.view.Component component) {
		AbstractAdapter<Component> adaptedComponent = (AbstractAdapter<Component>) component;

		Component vaadinComponent = adaptedComponent.getAdapted();

		this.getAdapted().getLayout().addComponent(vaadinComponent);

		if (vaadinComponent instanceof Field && component instanceof FormField) {
			this.getAdapted().addField(this.fields.size(),
					(Field) vaadinComponent);
			this.fields.add((FormField<?>) component);
		}
	}

	@Override
	public void setValue(String value) {
		this.getAdapted().setCaption(value);
	}

	@Override
	public String getValue() {
		return this.getAdapted().getCaption();
	}

	@Override
	public boolean isValid() {
		return this.getAdapted().isValid();
	}

	@Override
	public Iterable<FormField<?>> getFields() {
		return this.fields;
	}
}
