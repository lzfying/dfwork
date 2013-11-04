package ar.com.oxen.nibiru.sample.system.view;



import ar.com.oxen.nibiru.i18n.api.MessageSource;
import ar.com.oxen.nibiru.sample.system.RoleManager;
import ar.com.oxen.nibiru.sample.system.api.IRoleView;
import ar.com.oxen.nibiru.ui.api.view.ViewFactory;
import ar.com.oxen.nibiru.ui.vaadin.api.ApplicationAccessor;



public class RoleViewFactory {
	private ApplicationAccessor applicationAccessor;
	private MessageSource messageSource;
	private ViewFactory viewFactory;
	private RoleManager roleManager;
	
	public IRoleView buildRoleView() {
		return new RoleListView(applicationAccessor, this.viewFactory, this.messageSource,this.roleManager);
	}

	public ApplicationAccessor getApplicationAccessor() {
		return applicationAccessor;
	}

	public void setApplicationAccessor(ApplicationAccessor applicationAccessor) {
		this.applicationAccessor = applicationAccessor;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	public void setViewFactory(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	

	
	
}
