package ar.com.oxen.nibiru.sample.system;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.com.oxen.nibiru.security.manager.jpa.domain.Role;

public class JpaSecurityRoleManager implements RoleManager{
	private EntityManager entityManager;
	@Override
	public List<Role> getRoleList() {
		Query query = this.entityManager
				.createQuery("select u from Role u ");
		return query.getResultList();
	}

	@Override
	public void saveRole(Role role) {
		
		 try {
			 if(role.getId()!=null){
				 
				 Query query =this.entityManager.createQuery("select u from Role u where u.id = :id"); 
				 query.setParameter("id", role.getId());
				 Role trole = (Role) query.getSingleResult();
				 trole.setName(role.getName());
				 trole.setPermissions(role.getPermissions());
				 trole.setDescription(role.getDescription());
				 this.entityManager.persist(trole);
			 }else
			 	
			 this.entityManager.persist(role);
				
			 
		 }catch(Exception e){
			 
			 throw new RuntimeException("保存失败，请联系管理员！");

		 }
		
		
	}
	
	@Override
	public void updateRole(Role role) {
		this.entityManager.merge(role);
		
	}

	@Override
	public void deleteRole(Role role) {
		this.entityManager.remove(role);
		
	}

	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
