/**
 * 
 */
package com.eos.security.impl.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSPermissionService;
import com.eos.security.impl.dao.EOSPermissionDAO;
import com.eos.security.impl.model.EOSPermissionEntity;
import com.eos.security.impl.session.SessionContextManager;

/**
 * Permission Service default implementation.
 * 
 * @author santos.fabiano
 * 
 */
@Service
public class EOSPermissionServiceImpl implements EOSPermissionService {

	private static final Logger log = LoggerFactory
			.getLogger(EOSPermissionServiceImpl.class);

	private EOSPermissionDAO permissionDAO;

	@Autowired
	public void setPermissionDAO(EOSPermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}

	/**
	 * @see com.eos.security.api.service.EOSPermissionService#addRolePermissions(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void addRolePermissions(String code, List<String> permissions)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO validation, security, cache and messaging
		for (String permission : permissions) {
			createRolePermission(code, permission);
		}
	}

	private void createRolePermission(String code, String permission) {
		EOSPermissionEntity entity = new EOSPermissionEntity()
				.setRoleCode(code).setPermission(permission);
		permissionDAO.persist(entity);

		if (log.isDebugEnabled()) {
			log.debug("Adding permission: " + permission + " to role: " + code);
		}
	}

	/**
	 * @see com.eos.security.api.service.EOSPermissionService#removeRolePermission(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void removeRolePermission(String code, List<String> permissions)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO validation, security, cache and messaging
		permissionDAO.deleteRolePermissions(
				SessionContextManager.getCurrentTenantId(), code, permissions);
	}

	/**
	 * @see com.eos.security.api.service.EOSPermissionService#listRolePermissions(java.lang.String,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> listRolePermissions(String code, int limit, int offset) {
		// TODO validation, security, cache
		List<String> permissions = permissionDAO.listRolePermissions(
				SessionContextManager.getCurrentTenantId(), code);
		// TODO limit and offset as sublist
		return permissions;
	}

	/**
	 * @see com.eos.security.api.service.EOSPermissionService#hasPermission(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean hasPermission(String login, String permission) {
		Map<String, Boolean> permMap = hasPermissions(login,
				Arrays.asList(permission));
		return permMap.get(permission);
	}

	/**
	 * @see com.eos.security.api.service.EOSPermissionService#hasPermissions(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, Boolean> hasPermissions(String login,
			List<String> permissions) {
		// TODO validation, security, cache
		return null;
	}

}
