package ar.com.oxen.nibiru.sample.mainview;

import java.util.Locale;

import javax.sql.DataSource;

import ar.com.oxen.nibiru.application.api.main.MainView;
import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.sample.mainview.ui.TitleBar;
import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;
import ar.com.oxen.nibiru.ui.vaadin.application.NibiruApplication;
import ar.com.oxen.nibiru.ui.vaadin.view.adapter.MenuBarAdapter;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class CustomMainView implements MainView {
	private static String I18N_MENU_PREFIX = "ar.com.oxen.nibiru.app.menu.";
	private static String I18N_PREFIX = "ar.com.oxen.nibiru.sample.mainview.";
	private Locale locale;
	private ApplicationAccessor applicationAccessor;
	private MessageSource messageSource;

	private String curUserName;
	
	private Window window;
	private VerticalLayout mainLayout;
	private VerticalSplitPanel topVSplitPanel;
	private VerticalSplitPanel bottomVSplitPanel;
	private CssLayout headerLayout;
	private HorizontalLayout bootomLayout;
	private TitleBar titleBar;
	private HasMenuItems mainMenu;
	private MenuBar menuBar;
	// main tabsheet
	private TabSheet tabSheet;
	private Label userLabel=new Label();
	

	public CustomMainView(ApplicationAccessor applicationAccessor,
			ViewFactory viewFactory, MessageSource messageSource,
			Locale locale) {

		this.messageSource = messageSource;
		this.locale = locale;
		this.applicationAccessor = applicationAccessor;
		
		String appName = messageSource
				.getMessage(I18N_PREFIX + "appname");
		
		window = new Window(appName);
		window.setTheme("tailored");
		menuBar = new MenuBar();
		NibiruApplication aplication = (NibiruApplication)applicationAccessor.getApplication();
		aplication.setParameter("menubar", menuBar);
		
		if (appName != null) {
			mainLayout = new VerticalLayout();
			mainLayout.setImmediate(true);
			mainLayout.setWidth("100%");
			mainLayout.setHeight("950px");
			mainLayout.setMargin(false);
			mainLayout.setDebugId("mainPanel123");

			// create VerticalSplitPanel
			topVSplitPanel = buildTopVSplitPanel();
			mainLayout.addComponent(topVSplitPanel);

			window.addComponent(mainLayout);
		}
	}

	/**
	 * create top-level VerticalSplitPanel.
	 * 
	 * @return
	 */
	private VerticalSplitPanel buildTopVSplitPanel() {
		topVSplitPanel = new VerticalSplitPanel();
		topVSplitPanel.setSplitPosition(51, Sizeable.UNITS_PIXELS);
		topVSplitPanel.setImmediate(true);
		topVSplitPanel.setWidth("100.0%");
		topVSplitPanel.setHeight("100.0%");
		topVSplitPanel.setMargin(false);
		topVSplitPanel.setStyleName(Reindeer.SPLITPANEL_SMALL);
//		topVSplitPanel.setLocked(true);

		// create title region.
		topVSplitPanel.addComponent(getHeader());

		// create bottom & main content region.
		topVSplitPanel.addComponent(buildbottomVSplitPanel());

		return topVSplitPanel;
	}

	/**
	 * create top-level VerticalSplitPanel.
	 * 
	 * @return
	 */
	private VerticalSplitPanel buildbottomVSplitPanel() {
		bottomVSplitPanel = new VerticalSplitPanel();
		bottomVSplitPanel.setSplitPosition(96, Sizeable.UNITS_PERCENTAGE);
		bottomVSplitPanel.setImmediate(true);
		bottomVSplitPanel.setWidth("100.0%");
		bottomVSplitPanel.setHeight("100.0%");
		topVSplitPanel.setSizeFull();
		bottomVSplitPanel.setMargin(false);
		bottomVSplitPanel.setStyleName(Reindeer.SPLITPANEL_SMALL);
//		bottomVSplitPanel.setLocked(true);

		// create main content region.
		bottomVSplitPanel.addComponent(buildMainTabsheet());

		// create bottom region.
		bottomVSplitPanel.addComponent(getBottom());
				
		return bottomVSplitPanel;
	}
	
	/**
	 * Create title region.
	 * 
	 * @return
	 */
	public Layout getHeader() {

		try {
			headerLayout = new CssLayout();
			headerLayout.setWidth(100, CustomComponent.UNITS_PERCENTAGE);
			headerLayout.setImmediate(true);

			if (titleBar == null) {
				titleBar = new TitleBar(menuBar);
			}
			titleBar.init();
			headerLayout.addComponent(titleBar);

			return headerLayout;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Layout getBottom(){
		bootomLayout=new HorizontalLayout();
		bootomLayout.setWidth("100%");
		bootomLayout.setImmediate(true);
		bootomLayout.setMargin(true);
		bootomLayout.setSpacing(true);

		// Display User Name.
		Label userTipLabel = new Label();
		userTipLabel.setValue("<b><font size=3>" + messageSource
				.getMessage(I18N_PREFIX + "currentUser") + "</b></font>");
		
		Label copyrightLabel = new Label();
		copyrightLabel.setValue("<b><font size=3>" + messageSource
				.getMessage(I18N_PREFIX + "copyright") + "</b></font>");
		
		bootomLayout.addComponent(userTipLabel);
		bootomLayout.addComponent(userLabel);
		bootomLayout.addComponent(copyrightLabel);
		bootomLayout.setComponentAlignment(userTipLabel, Alignment.MIDDLE_LEFT);
		bootomLayout.setComponentAlignment(userLabel, Alignment.MIDDLE_LEFT);
		bootomLayout.setComponentAlignment(copyrightLabel, Alignment.MIDDLE_RIGHT);
		return bootomLayout;
	}

	private TabSheet buildMainTabsheet() {
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.setImmediate(true);
		window.setData(tabSheet);
		return tabSheet;
	}

	public TabSheet getMainContentTabSheet() {
		return this.tabSheet;
	}

	public Layout getMainLayout() {
		return this.mainLayout;
	}
	
	/**
	 * Set new title bar.
	 * 
	 * @param newTitleBar
	 */
	public void setTitleBar(TitleBar newTitleBar) {
		this.headerLayout.removeAllComponents();
		this.titleBar = newTitleBar;
		this.headerLayout.addComponent(newTitleBar);
	}

	@Override
	public HasMenuItems getMainMenu() {
		if (this.mainMenu == null) {
			synchronized (this) {
				if (this.mainMenu == null) {
					this.mainMenu = new MenuBarAdapter(menuBar);
				}
			}
		}

		return mainMenu;
	}

	@Override
	public MenuItem addMenuItem(String name, HasMenuItems parent, int position) {
		MenuItem item = null;
			item = parent.addMenuItem(this.messageSource.getMessage(
					I18N_MENU_PREFIX + name, locale), position);
		return item;
	}

	@Override
	public void setUserName(String userName) {
		this.curUserName=userName;
		userLabel.setValue("<b><font size=3>" + userName + "</b></font>");
		userLabel.setContentMode(Label.CONTENT_XHTML);
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
