package ar.com.oxen.nibiru.ui.utils.dialog;

import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.Button;
import ar.com.oxen.nibiru.ui.api.view.Label;
import ar.com.oxen.nibiru.ui.api.view.Panel;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.api.view.Window;

public class DialogBuilder {
	private ViewFactory viewFactory;
	private Window window;
	private Panel messagePanel;;
	private Panel buttonPanel;;

	public DialogBuilder(ViewFactory viewFactory) {
		super();
		this.viewFactory = viewFactory;

		this.window = viewFactory.buildWindow(null);
		this.window.setModal(true);

		this.messagePanel = viewFactory.buildVerticalPanel();
		this.window.addComponent(this.messagePanel);

		this.buttonPanel = viewFactory.buildHorizontalPanel();
		this.window.addComponent(this.buttonPanel);
	}

	public DialogBuilder title(String title) {
		this.window.setValue(title);
		return this;
	}

	public DialogBuilder message(String message) {
		Label<String> label = this.viewFactory.buildLabel(String.class);
		label.setValue(message);
		this.messagePanel.addComponent(label);
		return this;
	}

	public DialogBuilder button(String caption) {
		return this.button(caption, null);
	}

	public DialogBuilder button(String caption, final ClickHandler handler) {
		Button button = this.viewFactory.buildButton();
		button.setValue(caption);
		button.setClickHandler(new ClickHandler() {
			@Override
			public void onClick() {
				window.close();
				if (handler != null) {
					handler.onClick();
				}
			}
		});
		this.buttonPanel.addComponent(button);
		return this;
	}

	public Window build() {
		return this.window;
	}
}
