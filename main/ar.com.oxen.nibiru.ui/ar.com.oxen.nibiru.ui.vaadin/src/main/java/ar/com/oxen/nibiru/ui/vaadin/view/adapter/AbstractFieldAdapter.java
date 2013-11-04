package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import ar.com.oxen.nibiru.ui.api.mvp.ValueChangeHandler;
import ar.com.oxen.nibiru.validation.api.ValidationException;
import ar.com.oxen.nibiru.validation.api.Validator;
import ar.com.oxen.nibiru.validation.generic.NotEmptyValidator;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractField;

public abstract class AbstractFieldAdapter<T, K extends AbstractField> extends
		AbstractComponentAdapter<K> implements
		ar.com.oxen.nibiru.ui.api.view.FormField<T> {
	public AbstractFieldAdapter(K adapted) {
		super(adapted);
		this.getAdapted().setImmediate(true);
	}

	@Override
	public String getCaption() {
		return this.getAdapted().getCaption();
	}

	@Override
	public void setCaption(String caption) {
		this.getAdapted().setCaption(caption);
	}

	@Override
	public boolean isReadOnly() {
		return this.getAdapted().isReadOnly();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.getAdapted().setReadOnly(readOnly);
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		if (errorMessage != null) {
			this.getAdapted().setComponentError(new UserError(errorMessage));
		} else {
			this.getAdapted().setComponentError(null);
		}
	}

	@Override
	public void addValidator(Validator<T> validator) {
		// TODO: esto es horrible, pero Vaadin tiene un bug, si el campo esta
		// vacio, ignora a los validadores: http://dev.vaadin.com/ticket/8410
		if (validator instanceof NotEmptyValidator) {
			this.getAdapted().setRequired(true);
		} else {
			this.getAdapted().addValidator(new ValidatorAdapter<T>(validator));
		}
	}

	@Override
	public void removeValidator(Validator<T> validator) {
		// TODO: Agregado por "simetria" (dijo homero cuando le iban a cortar el
		// dedo) con el metodo anterior
		if (validator instanceof NotEmptyValidator) {
			this.getAdapted().setRequired(false);
		} else {
			this.getAdapted().removeValidator(
					new ValidatorAdapter<T>(validator));
		}
	}

	@Override
	public void validate() throws ValidationException {
		try {
			this.getAdapted().validate();
		} catch (InvalidValueExceptionAdapter e) {
			throw e.getValidationException();
		} catch (EmptyValueException e) {
			throw new ValidationException("required");
		} catch (InvalidValueException e) {
			throw new ValidationException(e.getMessage());
		}
	}

	@Override
	public void setValueChangeHandler(
			final ValueChangeHandler valueChangeHandler) {
		this.getAdapted().addListener(new ValueChangeListener() {
			private static final long serialVersionUID = -960496278173613224L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				valueChangeHandler.onValueChange();
			}
		});
	}
}
