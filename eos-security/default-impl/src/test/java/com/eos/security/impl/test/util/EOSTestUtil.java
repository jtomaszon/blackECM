/**
 * 
 */
package com.eos.security.impl.test.util;

import java.util.UUID;

import com.eos.common.exception.EOSException;
import com.eos.security.api.service.EOSSecurityService;
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
}
