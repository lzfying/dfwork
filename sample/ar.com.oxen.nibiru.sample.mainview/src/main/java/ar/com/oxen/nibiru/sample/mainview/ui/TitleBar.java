/*
 * Copyright (c) InforSuite CVICSE Middleware Co., LTD.
 * All rights reserved.
 */

package ar.com.oxen.nibiru.sample.mainview.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * <p>
 * 主界面上方标题区域。
 * <p>
 * 
 * @date 2013-5-6 <br>
 * @author li_zhi1 <br>
 * @version 9.0.0 <br>
 * 
 */
public class TitleBar extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	private Map<String, Button> menuItemButtons;

	private Label datetimeLabel;

	private Label logo;

	private Window aboutWindow;

	private MenuBar menuBar;

	public TitleBar(MenuBar menuBar) {
		menuItemButtons = new HashMap<String, Button>();
		this.menuBar = menuBar;
	}

	public void init() {
		setHeight(50, UNITS_PIXELS);
		setWidth("100%");
		setMargin(false, true, false, true);

		initLogo();
//		initProfileButton();
		initMenubar();
		initButtons();
	}

	/**
	 * Create LOGO region.
	 */
	private void initLogo() {
		logo = new Label();
		logo.setIcon(new ThemeResource("images/logo_tld.png"));
		logo.setWidth("220px");
		logo.setHeight("50px");
		addComponent(logo);
		setExpandRatio(logo, 0.12f);

	}

	/**
	 * Create user and domain info region.
	 */
	private void initProfileButton() {
		HorizontalLayout profileLayout = new HorizontalLayout();
		// profileLayout.setWidth("350px");
		profileLayout.setMargin(true);
		profileLayout.setSpacing(true);

		// Display User Name.
		Label userLabel = new Label();
		String cUser = "li_zhi1";
		userLabel.setValue("<b><font size=3>" + cUser + "</b></font>");
		userLabel.setContentMode(Label.CONTENT_XHTML);
		profileLayout.addComponent(userLabel);

		// Display Host Name.
		Label lineLabel = new Label();
		lineLabel.setValue("<b><font size=3>" + "|" + "</b></font>");
		lineLabel.setContentMode(Label.CONTENT_XHTML);
		profileLayout.addComponent(lineLabel);

		Label hostLabel = new Label();
		String hostName = "127.0.0.1";
		if (hostName == null) {
			hostName = "";
		}
		hostLabel.setValue("<b><font size=3>" + hostName + "</b></font>");
		hostLabel.setContentMode(Label.CONTENT_XHTML);
		profileLayout.addComponent(hostLabel);

		// Display Domain Name.
		Label lineLabel1 = new Label();
		lineLabel1.setValue("<b><font size=3>" + "|" + "</b></font>");
		lineLabel1.setContentMode(Label.CONTENT_XHTML);
		profileLayout.addComponent(lineLabel1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curDate = sdf.format(new Date());
		datetimeLabel = new Label("<b><font size=3>" + curDate + "</b></font>");
		datetimeLabel.setContentMode(Label.CONTENT_XHTML);
		profileLayout.addComponent(datetimeLabel);

		addComponent(profileLayout);
		setComponentAlignment(profileLayout, Alignment.MIDDLE_RIGHT);
		setExpandRatio(profileLayout, 0.3f);
	}

	/**
	 * Create function button region.
	 */
	protected void initMenubar() {
		HorizontalLayout menuButtonLayout = new HorizontalLayout();
		menuButtonLayout.setMargin(true);
		menuButtonLayout.setSpacing(true);
		
		Label space1 = new Label(
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		space1.setContentMode(Label.CONTENT_XHTML);
		menuButtonLayout.addComponent(space1);

		menuButtonLayout.addComponent(menuBar);
		
		Label space4 = new Label(
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		space4.setWidth("20px");
		space4.setContentMode(Label.CONTENT_XHTML);
		menuButtonLayout.addComponent(space4);
		
		addComponent(menuButtonLayout);
		setComponentAlignment(menuButtonLayout, Alignment.MIDDLE_RIGHT);
		setExpandRatio(menuButtonLayout, 0.3f);
	}

	/**
	 * Create function button region.
	 */
	protected void initButtons() {
		// TODO: fixed widths based on i18n strings?
		HorizontalLayout menuButtonLayout = new HorizontalLayout();
		menuButtonLayout.setWidth("100px");
		Label space1 = new Label(
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		space1.setContentMode(Label.CONTENT_XHTML);
		menuButtonLayout.addComponent(space1);

		Button loginOutButton = addMenuButton(null, "Logout",
				new ThemeResource("images/ASUI_banner_button_quite.png"),
				false, 65);
		menuItemButtons.put("MAIN_NAVIGATION_MANAGE", loginOutButton);
		menuButtonLayout.addComponent(loginOutButton);

		Label space4 = new Label(
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		space4.setWidth("20px");
		space4.setContentMode(Label.CONTENT_XHTML);
		menuButtonLayout.addComponent(space4);
		addComponent(menuButtonLayout);
		setComponentAlignment(menuButtonLayout, Alignment.MIDDLE_RIGHT);
	}

	protected Button addMenuButton(String type, String label, Resource icon,
			boolean active, float width) {
		Button button = new NativeButton(label);
		button.setStyleName("borderless");
		button.setIcon(icon);
		button.setWidth(width, UNITS_PIXELS);

		addComponent(button);
		setComponentAlignment(button, Alignment.BOTTOM_RIGHT);

		return button;
	}

	protected boolean useProfile() {
		return true;
	}

}
