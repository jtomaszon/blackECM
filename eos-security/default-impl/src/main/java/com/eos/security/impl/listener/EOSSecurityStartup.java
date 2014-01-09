/**
 * 
 */
package com.eos.security.impl.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eos.common.EOSState;
import com.eos.common.EOSUserType;
import com.eos.security.impl.dao.EOSPermissionDAO;
import com.eos.security.impl.dao.EOSRoleDAO;
import com.eos.security.impl.dao.EOSRoleUserDAO;
import com.eos.security.impl.dao.EOSTenantDAO;
import com.eos.security.impl.dao.EOSUserDAO;
import com.eos.security.impl.dao.EOSUserTenantDAO;
import com.eos.security.impl.model.EOSPermissionEntity;
import com.eos.security.impl.model.EOSRoleEntity;
import com.eos.security.impl.model.EOSRoleUserEntity;
import com.eos.security.impl.model.EOSTenantEntity;
import com.eos.security.impl.model.EOSUserEntity;
import com.eos.security.impl.model.EOSUserTenantEntity;
import com.eos.security.impl.service.internal.EOSKnownPermissions;
import com.eos.security.impl.service.internal.EOSSystemConstants;

/**
 * Startup service. Create application default tenant, user, role and
 * permissions.
 * 
 * @author santos.fabiano
 * 
 */
@Service
public class EOSSecurityStartup implements
		ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory
			.getLogger(EOSSecurityStartup.class);

	@Autowired
	private EOSTenantDAO tenantDAO;
	@Autowired
	private EOSUserDAO userDAO;
	@Autowired
	private EOSUserTenantDAO userTenantDAO;
	@Autowired
	private EOSRoleDAO roleDAO;
	@Autowired
	private EOSRoleUserDAO roleUserDAO;
	@Autowired
	private EOSPermissionDAO permissionDAO;

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextEvent) {
		log.info("### Starting EOS-Security ###");
		// Manually create tenant to avoid security checks
		createTenant();
		// Manually create default users and roles to avoid security checks
		createUsers();
		log.info("### EOS-Security startup complete ###");
	}

	private void createTenant() {
		// Default tenant
		EOSTenantEntity tenant = tenantDAO
				.find(EOSSystemConstants.ADMIN_TENANT);

		if (tenant == null) {
			log.debug("Creating EOS default tenant");
			// Use merge to force id on entity
			tenant = new EOSTenantEntity()
					.setId(EOSSystemConstants.ADMIN_TENANT)
					.setName("EOS Tenant")
					.setDescription("Administration tenant")
					.setState(EOSState.ACTIVE);
			tenantDAO.merge(tenant);
		}
	}

	private void createUsers() {
		// Create Administrator User
		EOSUserTenantEntity adminUser = createUser(
				EOSSystemConstants.LOGIN_SUPER_ADMIN, "Administrator",
				EOSUserType.USER);
		// Create Anonymous User
		createUser(EOSSystemConstants.LOGIN_ANONYMOUS, "Anonymous",
				EOSUserType.USER);

		// Super Administrator role
		EOSRoleEntity adminRole = createAdminRole();
		// Roles to users
		createRoleUser(adminUser.getLogin(), adminRole.getCode());
		// Create system user
		EOSUserTenantEntity systemUser = createUser(
				EOSSystemConstants.LOGIN_SYSTEM_USER, "System User",
				EOSUserType.SYSTEM);
		// system task user has the same permissions as super administrator
		createRoleUser(systemUser.getLogin(), adminRole.getCode());
	}

	private EOSUserTenantEntity createUser(String login, String name,
			EOSUserType type) {
		EOSUserEntity user = userDAO.checkedFind(login);

		if (user == null) {
			log.debug("Creating EOS default user " + login);
			user = new EOSUserEntity().setFirstName("EOS").setLastName(name)
					.setLogin(login).setEmail(login + "@eossecurity.com")
					.setType(type);

			userDAO.persist(user);
		}

		EOSUserTenantEntity userTenant = userTenantDAO.findByLogin(login,
				EOSSystemConstants.ADMIN_TENANT);

		if (userTenant == null) {
			log.debug("Creating EOS default user tenant " + login);
			userTenant = new EOSUserTenantEntity().setLogin(login).setState(
					EOSState.ACTIVE);

			userTenant.setTenantId(EOSSystemConstants.ADMIN_TENANT);
			userTenantDAO.persist(userTenant);
		}

		return userTenant;
	}

	private EOSRoleEntity createAdminRole() {
		EOSRoleEntity role = roleDAO.findByCode(
				EOSSystemConstants.ROLE_SUPER_ADMIN,
				EOSSystemConstants.ADMIN_TENANT);

		if (role == null) {
			log.debug("Creating EOS default administrator role ");
			role = new EOSRoleEntity()
					.setCode(EOSSystemConstants.ROLE_SUPER_ADMIN)
					.setDescription("EOS Administrator Role")
					.setLevel(EOSSystemConstants.INTERNAL_LEVEL);
			role.setTenantId(EOSSystemConstants.ADMIN_TENANT);

			roleDAO.persist(role);

			// Create Admin permission
			EOSPermissionEntity permissionAll = new EOSPermissionEntity()
					.setRoleCode(role.getCode()).setPermission(
							EOSKnownPermissions.PERMISSION_ALL);
			permissionAll.setTenantId(EOSSystemConstants.ADMIN_TENANT);
			permissionDAO.persist(permissionAll);
		}

		return role;
	}

	private void createRoleUser(String login, String code) {
		EOSRoleUserEntity roleUser = roleUserDAO.findByUserAndRole(login, code,
				EOSSystemConstants.ADMIN_TENANT);

		if (roleUser == null) {
			log.debug("Adding user " + login + "to role " + code);
			roleUser = new EOSRoleUserEntity().setRoleCode(code).setUserLogin(
					login);
			roleUser.setTenantId(EOSSystemConstants.ADMIN_TENANT);
			roleUserDAO.persist(roleUser);
		}
	}

}
