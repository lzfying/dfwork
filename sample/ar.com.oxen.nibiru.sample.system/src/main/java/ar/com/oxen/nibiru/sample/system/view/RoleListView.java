package ar.com.oxen.nibiru.sample.system.view;

import java.util.List;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.sample.layout.LayoutWindowAdapter;
import ar.com.oxen.nibiru.sample.system.RoleManager;
import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

public class RoleListView implements RolePresenter.Display {
	private static String I18N_PREFIX = "ar.com.oxen.nibiru.sample.system.";

	private LayoutWindowAdapter windowAdapter;
	private ApplicationAccessor applicationAccessor;

	
	private MessageSource messageSource;

	private VerticalLayout mainLayout;

	private GridLayout roleListGL;
	private Table roleListTable = null;// 显示构件列表的表格
	private HorizontalLayout toolBarLayout;
	private Button refreshBtn;
	private Button addBtn;
	private RoleManager roleManager;
	private List<Role> roleInfos = null;
	private Object rolePropertyName = null;
	private Object rolePropertyPermissions = null;
	private Object rolePropertyOperate = null;
	private Object rolePropertyDescription = null;

	public RoleListView(ApplicationAccessor applicationAccessor,
			ViewFactory viewFactory, MessageSource messageSource,
			RoleManager roleManager) {

		this.messageSource = messageSource;
		this.applicationAccessor = applicationAccessor;
		this.roleManager = roleManager;

		String appName = messageSource.getMessage(I18N_PREFIX + "appname");
		if (appName != null) {
			mainLayout = new VerticalLayout();
			mainLayout.setSizeUndefined();
			mainLayout.setMargin(false, false, false, false);
			mainLayout.setSizeFull();
			mainLayout.setCaption(messageSource.getMessage("ar.com.oxen.nibiru.app.menu.sample.role"));
			
			buildRoleListGL();
			mainLayout.addComponent(roleListGL);

			// window.setContent(mainLayout);

			this.windowAdapter = new LayoutWindowAdapter(mainLayout,
					applicationAccessor.getApplication().getMainWindow());

		}
	}

	private GridLayout buildRoleListGL() {
		roleListGL = new GridLayout(3, 3);
		roleListGL.setWidth("100%");
		roleListGL.setMargin(false, true, true, true);
		roleListGL.setStyleName("maingridleftmargin");
		roleListGL.addStyleName("maingridrightmargin");
		// toolBarLayout
		buildToolBarLayout();
		roleListGL.addComponent(toolBarLayout, 2, 0);
		roleListGL.setComponentAlignment(toolBarLayout, Alignment.BOTTOM_RIGHT);

		rolePropertyName = messageSource.getMessage(I18N_PREFIX + "rolename");
		rolePropertyPermissions = messageSource.getMessage(I18N_PREFIX
				+ "permission");
		rolePropertyOperate = messageSource.getMessage(I18N_PREFIX + "operate");
		rolePropertyDescription = messageSource.getMessage(I18N_PREFIX
				+ "description");

		// roleListTable
		roleListTable = new Table();
		roleListTable.setWidth("100%");
		roleListTable.setSelectable(false);
		roleListTable.setMultiSelect(true);
		roleListTable.setImmediate(true);
		roleListTable.setColumnWidth("select", 30);
		roleListTable.setColumnCollapsingAllowed(true);
		roleListTable.setColumnReorderingAllowed(true);
		roleListTable.setContainerDataSource(getRoleListDataSource());
		roleListGL.addComponent(roleListTable, 0, 1, 2, 1);

		return roleListGL;
	}

	// 工具栏
	private HorizontalLayout buildToolBarLayout() {
		// common part: create layout
		toolBarLayout = new HorizontalLayout();
		toolBarLayout.setSpacing(true);
		toolBarLayout.setStyleName("buttonspace");
		toolBarLayout.setHeight("40px");

		// 初始化构件服务器名称检索文本框
		final TextField userNameTxt = new TextField();
		userNameTxt.setStyleName("search");

		userNameTxt.setInputPrompt(messageSource.getMessage(I18N_PREFIX
				+ "filter"));
		userNameTxt.setWidth("312px");
		userNameTxt.setHeight("28px");
		userNameTxt.setImmediate(true);
		userNameTxt.setTextChangeEventMode(TextChangeEventMode.LAZY);
		userNameTxt.setTextChangeTimeout(50);
		userNameTxt.addListener(new TextChangeListener() {
			private static final long serialVersionUID = -6730979899365791928L;

			public void textChange(TextChangeEvent event) {
				IndexedContainer nameContainer = (IndexedContainer) roleListTable
						.getContainerDataSource();
				nameContainer.removeAllContainerFilters();
				String nameStr = event.getText();
				if (nameStr != null && !"".equals(nameStr.trim()))
					nameContainer.addContainerFilter(rolePropertyName, nameStr,
							true, false);
				roleListTable.setPageLength(nameContainer.size());
			}
		});
		toolBarLayout.addComponent(userNameTxt);

		// 添加
		addBtn = new NativeButton();
		addBtn.setCaption(messageSource.getMessage(I18N_PREFIX + "add"));
		addBtn.setImmediate(true);
		addBtn.setWidth("60px");
		addBtn.setStyleName("blue");
		addBtn.addStyleName("white");
		addBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				Window addWindow = new UpdateRoleView(applicationAccessor,
						roleManager, messageSource, null);
				addWindow.setModal(true);
				addBtn.getWindow().addWindow(addWindow);
				addWindow.addListener(new CloseListener(){
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent e) {
						refreshTable();
					}
					
				});
			}
		});
		toolBarLayout.addComponent(addBtn);

		// 刷新
		refreshBtn = new NativeButton();
		refreshBtn
				.setCaption(messageSource.getMessage(I18N_PREFIX + "refresh"));
		refreshBtn.setImmediate(true);
		refreshBtn.setWidth("60px");
		refreshBtn.setStyleName("blue");
		refreshBtn.addStyleName("white");
		refreshBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				refreshTable();
				if (userNameTxt != null) {
					userNameTxt.setValue("");
				}
			}
		});
		toolBarLayout.addComponent(refreshBtn);

		return toolBarLayout;
	}

	/**
	 * 设置Roles数据源
	 * 
	 * @return
	 */
	private IndexedContainer getRoleListDataSource() {
		IndexedContainer rolesContainer = null;
		if (rolesContainer == null) {
			rolesContainer = new IndexedContainer();
			rolesContainer.addContainerProperty(rolePropertyName, String.class,
					null);
			rolesContainer.addContainerProperty(rolePropertyPermissions,
					String.class, null);
			rolesContainer.addContainerProperty(rolePropertyDescription,
					String.class, null);
			rolesContainer.addContainerProperty(rolePropertyOperate,
					HorizontalLayout.class, null);
		} else {
			rolesContainer.removeAllItems();
		}
		try {
			roleInfos = roleManager.getRoleList();
			for (Role roleInfo : roleInfos) {
				String userInfoName = roleInfo.getName();
				String permissions = roleInfo.getPermissions();
				String descripton = roleInfo.getDescription();

				HorizontalLayout operate_layout = new HorizontalLayout();
				operate_layout.setSpacing(true);
//				operate_layout.setSizeFull();
//				Button modifyBtn = new Button(
//						messageSource.getMessage(I18N_PREFIX + "modify"));	
//				modifyBtn.setStyleName(BaseTheme.BUTTON_LINK);
				Button modifyBtn = new Button();	
				modifyBtn.setIcon(new ThemeResource(
    					"icons/edit.gif"));
				modifyBtn.setStyleName(BaseTheme.BUTTON_LINK);
				modifyBtn.setCaption("");
				modifyBtn.setDescription(messageSource.getMessage(I18N_PREFIX + "modify"));
//				Button deleteBtn = new Button(
//						messageSource.getMessage(I18N_PREFIX + "delete"));
//				deleteBtn.setStyleName(BaseTheme.BUTTON_LINK);
				operate_layout.addComponent(modifyBtn);
				operate_layout.setComponentAlignment(modifyBtn, Alignment.MIDDLE_CENTER);
//				operate_layout.addComponent(deleteBtn);

				ClickListenerHandler clickListenterModify = new ClickListenerHandler(
						modifyBtn, roleInfo);
//				ClickListenerHandler clickListenterDelete = new ClickListenerHandler(
//						deleteBtn, roleInfo);
				// 给按钮加监听
				modifyBtn.setImmediate(true);
				modifyBtn.addListener(clickListenterModify);
//				deleteBtn.setImmediate(true);
//				deleteBtn.addListener(clickListenterDelete);

				Item item = rolesContainer.addItem(roleInfo);
				if (item != null) {
					item.getItemProperty(rolePropertyName).setValue(
							userInfoName);
					item.getItemProperty(rolePropertyPermissions).setValue(
							permissions);
					item.getItemProperty(rolePropertyDescription).setValue(
							descripton);
					item.getItemProperty(rolePropertyOperate).setValue(
							operate_layout);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		roleListTable.setPageLength(roleInfos.size());
		return rolesContainer;
	}

	// 刷新表格
	private synchronized void refreshTable() {
		roleListTable.setContainerDataSource(getRoleListDataSource());
	}

	private class ClickListenerHandler implements ClickListener {

		private static final long serialVersionUID = 1L;

		private Button modifyBt;
		private String roleCode = "";
		private Role roleInfo;

		public ClickListenerHandler(Button modifyBt, Role roleInfo) {
			this.modifyBt = modifyBt;
			if (roleInfo != null) {
				this.roleInfo = roleInfo;
				this.roleCode = roleInfo.getName() == null ? "" : roleInfo
						.getName();
			}
		}

		@Override
		public void buttonClick(ClickEvent event) {
			final Button btn = event.getButton();
			if (messageSource.getMessage(I18N_PREFIX + "delete").equals(
					event.getButton().getCaption())) {

				try {
					// 修改为根据用户的角色进行判断
					roleManager.deleteRole(roleInfo);
				} catch (Exception e) {
					event.getButton()
							.getApplication()
							.getMainWindow()
							.showNotification(
									messageSource.getMessage(I18N_PREFIX
											+ "error"), e.getMessage(),
									Notification.TYPE_ERROR_MESSAGE);
				}
				refreshTable();

			} else {
				applicationAccessor.getApplication().getMainWindow();
				Window editWindow = new UpdateRoleView(applicationAccessor,
						roleManager, messageSource, roleInfo);
				editWindow.setModal(true);
				modifyBt.getWindow().addWindow(editWindow);
				editWindow.addListener(new CloseListener(){
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent e) {
						refreshTable();
					}
					
				});
			}
		}

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandler getFreshBtn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandler getAddBtn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandler getEditBtn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasCloseHandler getClose() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
