package ar.com.oxen.nibiru.sample.system;

import java.util.List;

import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;

public interface RoleManager {
	/**
	 * 获取全部角色
	 * @return
	 */
	public List<Role> getRoleList();
	
	/**
	 * 增加一个角色 
	 * @param role
	 */
	public void saveRole(Role role);
	
	/**
	 * 删除一个角色
	 * @param role
	 * @return
	 */
	public void deleteRole(Role role);
	
	
	/**
	 * 修改一个角色
	 * @param role
	 */
	public void updateRole(Role role) ;
	
	

}
