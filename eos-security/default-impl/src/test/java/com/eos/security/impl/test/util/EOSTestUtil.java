/**
 * 
 */
package com.eos.security.impl.test.util;

import java.util.Map;
import java.util.UUID;

import com.eos.common.EOSLevel;
import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSGroupService;
import com.eos.security.api.service.EOSRoleService;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.impl.service.EOSSystemConstants;
import com.eos.security.impl.session.SessionContextManager;

/**
 * Test utilities.
 * 
 * @author santos.fabiano
 * 
 */
public class EOSTestUtil {

	/**
	 * Setup session context for testing. Set default tenant.
	 * 
	 * @param svcSecurity
	 * @throws EOSException
	 */
	public static void setup(EOSSecurityService svcSecurity)
			throws EOSException {
		if (SessionContextManager.getCurrentSession() == null
				|| SessionContextManager.getCurrentSession().getTenant() == null) {
			svcSecurity.createSessionContext(UUID.randomUUID().toString(),
					EOSSystemConstants.ADMIN_TENANT);
		}
	}

	public static EOSUser createUser(String prefix,
			Map<String, String> userData, EOSUserService svcUser)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSUser user = new EOSUser();
		user.setLogin(prefix + "_create").setEmail(prefix + "@create.com")
				.setFirstName(prefix + " First").setLastName(prefix + " Last")
				.setNickName(prefix + " Nick")
				.setPersonalMail(prefix + "@personal.com")
				.setState(EOSState.ACTIVE);

		user = svcUser.createUser(user, userData);
		return user;
	}

	public static EOSGroup createGroup(String identifier,
			EOSGroupService svcGroup) throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSGroup group = new EOSGroup().setName("Test " + identifier)
				.setDescription("Test description " + identifier)
				.setLevel(EOSLevel.PUBLIC.getLevel());

		return svcGroup.createGroup(group);
	}

	public static EOSRole createRole(String identifier, EOSRoleService svcRole)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSRole role = new EOSRole().setCode(identifier + "_code")
				.setDescription(identifier + " Description")
				.setLevel(EOSLevel.MAXIMUM.getLevel());
		return svcRole.createRole(role);
	}
}
