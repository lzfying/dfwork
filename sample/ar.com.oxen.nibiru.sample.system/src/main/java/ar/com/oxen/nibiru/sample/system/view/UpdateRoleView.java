package ar.com.oxen.nibiru.sample.system.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.sample.system.RoleManager;
import ar.com.oxen.nibiru.sample.system.ui.SelectAllItemsListener;
import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;
import ar.com.oxen.nibiru.ui.vaadin.application.NibiruApplication;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * <p>
 * 添加或修改角色界面。
 * <p>
 * 
 * @date 2013-7-24 <br>
 * @author he_lyun <br>
 * @version 9.0.0 <br>
 * 
 */
public class UpdateRoleView extends Window implements
		Property.ValueChangeListener {
	private static final long serialVersionUID = 1L;

	private static String I18N_PREFIX = "ar.com.oxen.nibiru.sample.system.";
	private RoleManager roleManager;
	private MessageSource messageSource;
	private ApplicationAccessor applicationAccessor;
	private Role roleInfo;
	private Role copyRole;
	private boolean selected = false;

	private VerticalLayout mainVLayout;

	private HorizontalLayout toolBarLayout;
	private Button commitBtn;
	private Button cancelBtn;

	private HorizontalLayout role_layout;
	private TextField role_text;

	private HorizontalLayout description_layout;
	private TextField description_text;

	private HorizontalLayout resource_layout;

	private GridLayout roleGL;
	private TreeTable permissionsTree;

	public UpdateRoleView(ApplicationAccessor applicationAccessor,
			RoleManager roleManager, MessageSource messageSource, Role role) {
		this.setWidth("800px");
		this.setHeight("600px");

		this.applicationAccessor = applicationAccessor;
		this.roleManager = roleManager;
		this.messageSource = messageSource;
		this.copyRole = null;
		this.roleInfo = role;
		if (role != null) {
			copyRole = new Role();
			copyRole.setDescription(role.getDescription());
			copyRole.setId(role.getId());
			copyRole.setName(role.getName());
			copyRole.setPermissions(role.getPermissions());
		}
		buildMainLayout();
		this.addComponent(mainVLayout);
	}

	private VerticalLayout buildMainLayout() {
		// the main layout and components will be created here
		mainVLayout = new VerticalLayout();
		mainVLayout.setSizeUndefined();
		mainVLayout.setMargin(false, false, false, false);
		mainVLayout.setSizeFull();

		buildRoleGL();

		mainVLayout.addComponent(roleGL);
		return mainVLayout;
	}

	private void buildRoleGL() {
		roleGL = new GridLayout(2, 5);
		roleGL.setWidth("100%");
		roleGL.setMargin(false, true, true, true);
		roleGL.setStyleName("maingridleftmargin");
		roleGL.addStyleName("maingridrightmargin");

		// toolBar Layout
		buildToolBarLayout();
		roleGL.addComponent(toolBarLayout, 1, 0);
		roleGL.setComponentAlignment(toolBarLayout, Alignment.BOTTOM_RIGHT);

		// Role Name Layout
		buildRoleNameLayout();
		roleGL.addComponent(role_layout, 0, 1, 1, 1);

		// Description Layout
		buildDescriptionLayout();
		roleGL.addComponent(description_layout, 0, 2, 1, 2);

		// check all
		buildCheckInfo();
		roleGL.addComponent(resource_layout, 0, 3, 1, 3);

		buildPermissionTree();
		roleGL.addComponent(permissionsTree, 0, 4, 1, 4);
	}

	// 工具栏
	private HorizontalLayout buildToolBarLayout() {
		// common part: create layout
		toolBarLayout = new HorizontalLayout();
		toolBarLayout.setSpacing(true);
		toolBarLayout.setStyleName("buttonspace");
		toolBarLayout.setHeight("40px");

		// 保存
		commitBtn = new NativeButton();
		commitBtn.setCaption(messageSource.getMessage(I18N_PREFIX + "commit"));
		commitBtn.setImmediate(true);
		commitBtn.setWidth("60px");
		commitBtn.setStyleName("blue");
		commitBtn.addStyleName("white");
		commitBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				String roleCode = (String) role_text.getValue();
				// 角色不能为空
				if (roleCode == null || "".equals(roleCode.trim())) {
					commitBtn.getWindow().showNotification(
							messageSource.getMessage(I18N_PREFIX + "error"),
							"role is null.", Notification.TYPE_ERROR_MESSAGE);
					return;
				}

				String description = description_text.getValue().toString();

				// 权限
				String permissions = "";
				if (selected) {
					permissions = "*";
				} else {
					IndexedContainer appContainer = (IndexedContainer) permissionsTree
							.getContainerDataSource();
					for (int i = 0; i < appContainer.size(); i++) {
						Item item = appContainer.getItem(appContainer
								.getIdByIndex(i));
						CheckBox chkbx = (CheckBox) item.getItemProperty(
								"select").getValue();
						if (chkbx.booleanValue()) {
							String permission = item
									.getItemProperty("menuname").getValue()
									.toString();
							permissions = permissions + "," + permission;
						}
					}
					if (!"".equals(permissions)) {
						permissions = permissions.substring(1);
					}
				}

				try {
					if (roleInfo != null) {
						roleInfo.setName(roleCode);
						roleInfo.setDescription(description);
						roleInfo.setPermissions(permissions);
						roleManager.saveRole(roleInfo);
						commitBtn.getWindow().showNotification(
								null,
								messageSource.getMessage(I18N_PREFIX
										+ "save.success"),
								Notification.TYPE_HUMANIZED_MESSAGE);
						applicationAccessor
								.getApplication()
								.getMainWindow()
								.showNotification(
										null,
										messageSource.getMessage(I18N_PREFIX
												+ "save.success"),
										Notification.TYPE_HUMANIZED_MESSAGE);
					} else {
						Role addRole = new Role();
						addRole.setName(roleCode);
						addRole.setDescription(description);
						addRole.setPermissions(permissions);
						roleManager.saveRole(addRole);
						applicationAccessor
								.getApplication()
								.getMainWindow()
								.showNotification(
										null,
										messageSource.getMessage(I18N_PREFIX
												+ "save.success"),
										Notification.TYPE_HUMANIZED_MESSAGE);
					}
					commitBtn.getWindow().getParent()
							.removeWindow(commitBtn.getWindow());
				} catch (Exception e) {
					commitBtn.getWindow().showNotification(
							messageSource.getMessage(I18N_PREFIX + "error"),
							messageSource.getMessage(I18N_PREFIX
									+ "save.failed"),
							Notification.TYPE_ERROR_MESSAGE);
					return;
				}

			}
		});
		toolBarLayout.addComponent(commitBtn);
		// 取消
		cancelBtn = new NativeButton();
		cancelBtn.setCaption(messageSource.getMessage(I18N_PREFIX + "cancel"));
		cancelBtn.setImmediate(true);
		cancelBtn.setWidth("60px");
		cancelBtn.setStyleName("blue");
		cancelBtn.addStyleName("white");
		cancelBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				cancelBtn.getWindow().getParent()
						.removeWindow(cancelBtn.getWindow());
			}
		});

		toolBarLayout.addComponent(cancelBtn);

		return toolBarLayout;
	}

	// 角色名
	private void buildRoleNameLayout() {
		role_layout = new HorizontalLayout();
		role_layout.setHeight("40px");
		role_layout.setSpacing(true);
		Label resource_label = new Label(messageSource.getMessage(I18N_PREFIX
				+ "rolename"));
		resource_label.setContentMode(Label.CONTENT_XHTML);
		resource_label.setWidth("75px");
		role_text = new TextField();
		role_text.setWidth("270px");
		role_text.setRequired(true);
		role_text.setValue(roleInfo == null ? "" : roleInfo.getName());
		role_text.setImmediate(true);

		role_layout.addComponent(resource_label);
		role_layout
				.setComponentAlignment(resource_label, Alignment.MIDDLE_LEFT);
		role_layout.addComponent(role_text);
		role_layout.setComponentAlignment(role_text, Alignment.MIDDLE_LEFT);

	}

	// 角色描述
	private void buildDescriptionLayout() {
		description_layout = new HorizontalLayout();
		description_layout.setHeight("40px");
		description_layout.setSpacing(true);
		Label resource_label = new Label(messageSource.getMessage(I18N_PREFIX
				+ "description"));
		resource_label.setContentMode(Label.CONTENT_XHTML);
		resource_label.setWidth("75px");
		description_text = new TextField();
		description_text.setWidth("270px");
		description_text.setValue(roleInfo == null ? "" : roleInfo
				.getDescription());
		description_text.setImmediate(true);

		description_layout.addComponent(resource_label);
		description_layout.setComponentAlignment(resource_label,
				Alignment.MIDDLE_LEFT);
		description_layout.addComponent(description_text);
		description_layout.setComponentAlignment(description_text,
				Alignment.MIDDLE_LEFT);

	}

	// 资源列表
	private void buildCheckInfo() {
		resource_layout = new HorizontalLayout();
		resource_layout.setHeight("40px");
		Label resource_label = new Label("<b>"
				+ messageSource.getMessage(I18N_PREFIX + "resourcelist")
				+ "</b>");
		resource_label.setContentMode(Label.CONTENT_XHTML);
		resource_layout.addComponent(resource_label);
		resource_layout.setComponentAlignment(resource_label,
				Alignment.MIDDLE_LEFT);
	}

	private void buildPermissionTree() {
		permissionsTree = new TreeTable();
		permissionsTree.setSizeFull();
		permissionsTree.setSelectable(false);
		permissionsTree.setMultiSelect(true);
		permissionsTree.setImmediate(true);
		permissionsTree.setColumnCollapsingAllowed(false);
		permissionsTree.setColumnReorderingAllowed(true);
		permissionsTree.setColumnWidth("select", 60);
		permissionsTree.setColumnHeader("select", "");
		permissionsTree.setColumnIcon("select", new ThemeResource(
				"icons/uncheck.png"));
		permissionsTree.addListener(new SelectAllItemsListener("select"));
		permissionsTree.setColumnHeader("menuname",
				messageSource.getMessage(I18N_PREFIX + "menuname"));
		permissionsTree.setContainerDataSource(getPermissionData());

	}

	private HierarchicalContainer getPermissionData() {
		// 带索引的容器，用来排序和检索
		HierarchicalContainer permissionContainer = new HierarchicalContainer();
		permissionContainer
				.addContainerProperty("select", CheckBox.class, null);
		permissionContainer
				.addContainerProperty("menuname", String.class, null);
		int pageLength = 0;

		NibiruApplication aplication = (NibiruApplication) applicationAccessor
				.getApplication();
		MenuBar menubar = (MenuBar) aplication.getParameter("menubar");
		if (menubar == null) {
			// permissionsTree.setPageLength(pageLength);
			return permissionContainer;
		}

		List<MenuItem> rootItems = menubar.getItems();
		if (rootItems == null) {
			// permissionsTree.setPageLength(pageLength);
			return permissionContainer;
		}
		pageLength = rootItems.size();

		for (MenuItem menuItem : rootItems) {
			if (menuItem != null) {
				Item treeRootItem = permissionContainer.addItem(menuItem);
				CheckBox chkbx = new CheckBox();
				chkbx.addListener(this);
				chkbx.setImmediate(true);
				chkbx.setValue(hasPermission(menuItem.getText()));
				treeRootItem.getItemProperty("select").setValue(chkbx);
				treeRootItem.getItemProperty("menuname").setValue(
						menuItem.getText());

				List<MenuItem> subItems = menuItem.getChildren();
				if (subItems != null) {
					for (MenuItem subItem : subItems) {
						if (subItem != null) {
							Item treeSubItem = permissionContainer
									.addItem(subItem);
							chkbx = new CheckBox();
							chkbx.addListener(this);
							chkbx.setImmediate(true);
							chkbx.setValue(hasPermission(subItem.getText()));
							treeSubItem.getItemProperty("select").setValue(
									chkbx);
							treeSubItem.getItemProperty("menuname").setValue(
									subItem.getText());
							permissionContainer.setParent(subItem, menuItem);
						}
					}
				}
			}
		}

		// permissionsTree.setPageLength(pageLength);
		return permissionContainer;
	}

	private boolean hasPermission(String permission) {
		String[] resourceArr = null;
		if (roleInfo == null) {
			return false;
		}
		String permissions = roleInfo.getPermissions();
		if (permissions == null) {
			return false;
		}
		if ("*".equals(permissions)) {
			return true;
		}

		resourceArr = permissions.split(",");
		if (resourceArr != null && resourceArr.length > 0) {
			for (String str : resourceArr) {
				if ("*".equals(str) || str.equals(permission)) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		IndexedContainer appContainer = (IndexedContainer) permissionsTree.getContainerDataSource();
        for (int i = 0; i < appContainer.size(); i++) {
            Item item = appContainer.getItem(appContainer.getIdByIndex(i));
            CheckBox chkbx = (CheckBox) item.getItemProperty("select").getValue();
            if (!chkbx.booleanValue()) {
            	permissionsTree.setColumnIcon("select", new ThemeResource(
    					"icons/uncheck.png"));
    			selected = false;
    			return;
			}else{
				permissionsTree.setColumnIcon("select", new ThemeResource(
						"icons/checked.png"));
				selected = true;
			}
        }
        
//		Iterator iterator = permissionsTree.rootItemIds().iterator();
//		while (iterator.hasNext()) {
//			MenuItem itemId = (MenuItem) iterator.next();
//			boolean result = getCheckBoxValue(itemId);
//			if (!result) {
//				return;
//			}
//		}

//		permissionsTree.setColumnIcon("select", new ThemeResource(
//				"icons/checked.png"));
//		selected = true;
	}

	private boolean getCheckBoxValue(MenuItem itemId) {
		boolean result = true;

		Item item = permissionsTree.getItem(itemId);

		CheckBox checkBox = (CheckBox) item.getItemProperty("select")
				.getValue();
		if (!checkBox.booleanValue()) {
			permissionsTree.setColumnIcon("select", new ThemeResource(
					"icons/uncheck.png"));
			selected = false;
			return false;
		}

		Collection childrenItemIds = permissionsTree.getChildren(itemId);
		if (childrenItemIds != null && childrenItemIds.size() > 0) {
			Iterator iterator = childrenItemIds.iterator();
			while (iterator.hasNext()) {
				MenuItem subItemId = (MenuItem) iterator.next();
				result = getCheckBoxValue(subItemId);
				if (!result) {
					permissionsTree.setColumnIcon("select", new ThemeResource(
							"icons/uncheck.png"));
					selected = false;
					return result;
				}
			}
		}

		return result;

	}

}
