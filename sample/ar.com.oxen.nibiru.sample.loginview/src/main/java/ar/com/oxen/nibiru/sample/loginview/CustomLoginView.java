package ar.com.oxen.nibiru.sample.loginview;

import java.io.InputStream;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.security.ui.api.LoginView;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.utils.mvp.HasValueI18nDecorator;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.ButtonAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.LabelAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.PasswordFieldAdapter;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.TextFieldAdapter;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.ShortcutAction;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class CustomLoginView implements LoginView {
	private static String I18N_PREFIX = "com.tailored.itop.demo.";
	private TextField userField;
	private PasswordField passwordField;
	private Button loginButton;
	private Label errorLabel;
	private ApplicationAccessor applicationAccessor;
	private Window window;
	private MessageSource messageSource;
	private String user;
	private String password;
	
	public CustomLoginView(ApplicationAccessor applicationAccessor,ViewFactory viewFactory, MessageSource messageSource) {
		this.messageSource = messageSource;
		this.applicationAccessor = applicationAccessor;
		
		String appName = messageSource
				.getMessage(I18N_PREFIX + "appname");
		window = new Window(appName);
		window.setTheme("tailored");
		// This is actually the default.
		window.setContent(new VerticalLayout());
		// Set the size of the root layout to full width and height.
		window.getContent().setSizeFull();

		try {
			String titleLabel;
			if (appName != null) {
				titleLabel = appName + " - "
						+ messageSource.getMessage(I18N_PREFIX + "login");
			} else {
				titleLabel = messageSource.getMessage(I18N_PREFIX + "login");
			}

			CustomLayout loginFormLayout = new CustomLayout(getLoginResource());
			// Use it as the layout of the Window.
			window.setContent(loginFormLayout);
			loginFormLayout.setSizeFull();

			// Create properties
			user = "";
			password = "";
			userField = new TextField(
					messageSource.getMessage(I18N_PREFIX + "login.username") + ":",
					new ObjectProperty(user));
			userField.setCaption(null);
			userField.setStyleName("LrMm");
			userField.setInputPrompt(messageSource
					.getMessage(I18N_PREFIX + "login.input.username"));
			userField.setWidth("312px");
			userField.setHeight("33px");
			userField.setWordwrap(false);

			this.passwordField = new PasswordField(
					messageSource.getMessage(I18N_PREFIX + "login.password") + ":",
					new ObjectProperty(password));
			passwordField.setCaption(null);
			passwordField.setStyleName("LrMm");
			passwordField.setInputPrompt(messageSource
					.getMessage(I18N_PREFIX + "login.input.password"));
			passwordField.setWidth("312px");
			passwordField.setHeight("33px");
			
			loginButton = new Button();
			loginButton.setStyleName(BaseTheme.BUTTON_LINK);
			loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
			loginButton.setIcon(new ThemeResource(
					"icons/login_button_red.gif"));

			loginFormLayout.addComponent(userField, "username");
			loginFormLayout.addComponent(passwordField, "passwd");
			loginFormLayout.addComponent(loginButton, "loginbtn");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the inputStream of html login page.
	 * 
	 * @return
	 * @throws Exception
	 */
	public InputStream getLoginResource() throws Exception {
		String loginStr = "/html/login.html";
		InputStream loginInputStream = getClass().getClassLoader()
				.getResourceAsStream(loginStr);
		return loginInputStream;
	}
	
	@Override
	public HasValue<String> getUserField() {
		TextFieldAdapter<String> text = new TextFieldAdapter<String>(
				this.userField);
		return text;
	}

	@Override
	public HasValue<String> getPasswordField() {
		PasswordFieldAdapter<String> text = new PasswordFieldAdapter<String>(
				this.passwordField);
		return text;
	}

	@Override
	public HasClickHandler getLoginButton() {
		ButtonAdapter boton = new ButtonAdapter(this.loginButton);
		return boton;
	}

	@Override
	public HasValue<String> getErrorMessage() {
		LabelAdapter<String> text = new LabelAdapter<String>(this.errorLabel);
		return new HasValueI18nDecorator(text, this.messageSource);
	}

	@Override
	public void show() {
		Window currentMainWindow = this.applicationAccessor.getApplication()
				.getMainWindow();
		if (currentMainWindow != null) {
			this.applicationAccessor.getApplication().removeWindow(
					currentMainWindow);
		}

		this.applicationAccessor.getApplication().setMainWindow(window);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
}
