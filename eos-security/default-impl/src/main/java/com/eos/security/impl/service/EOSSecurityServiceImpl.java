/**
 * 
 */
package com.eos.security.impl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.service.EOSTenantService;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.session.SessionContext;
import com.eos.security.impl.session.EOSSession;
import com.eos.security.impl.session.SessionContextManager;

/**
 * EOS Security Service implementation.
 * 
 * @author santos.fabiano
 * 
 */
@Service
public class EOSSecurityServiceImpl implements EOSSecurityService {

	private EOSTenantService svcTenant;
	private EOSUserService svcUser;

	@Autowired
	public void setTenantService(EOSTenantService svcTenant) {
		this.svcTenant = svcTenant;
	}

	@Autowired
	public void setUserService(EOSUserService svcUser) {
		this.svcUser = svcUser;
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#createSessionContext(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public final SessionContext createSessionContext(String sessionId,
			Long tenantId) {
		final SessionContext context = new SessionContext(
				svcTenant.findTenant(tenantId), svcUser.findTenantUser(
						EOSSystemConstants.LOGIN_ANONYMOUS, tenantId));
		final EOSSession session = EOSSession.getContext();
		// Set current session
		session.setSessionId(sessionId).setSession(context);
		// Add to local session cache
		SessionContextManager.setSession(sessionId, context);
		return session.getSession();
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#getSessionContext(java.lang.String)
	 */
	@Override
	public final SessionContext getSessionContext(String sessionId)
			throws EOSNotFoundException {
		SessionContext session = SessionContextManager.getSession(sessionId);

		// If session do not exist, create default session and return
		if (session == null) {
			return createSessionContext(sessionId,
					EOSSystemConstants.ADMIN_TENANT);
		} else {
			// return current session
			return session;
		}
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#login(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void login(String login, String password) throws EOSException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#logout()
	 */
	@Override
	public void logout() throws EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#isLogged()
	 */
	@Override
	public boolean isLogged() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#hasPermission(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean hasPermission(String login, String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#hasPermissions(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public Map<String, Boolean> hasPermissions(String login,
			List<String> permissions) {
		// TODO Auto-generated method stub
		return null;
	}

}
