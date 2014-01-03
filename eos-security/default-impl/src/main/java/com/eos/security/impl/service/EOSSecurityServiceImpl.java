/**
 * 
 */
package com.eos.security.impl.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eos.common.EOSUserType;
import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.service.EOSTenantService;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.session.SessionContext;
import com.eos.security.api.vo.EOSUser;
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

	private static final Logger log = LoggerFactory
			.getLogger(EOSSecurityServiceImpl.class);

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
			Long tenantId) throws EOSException {
		try {
			return createSessionContext(sessionId, tenantId,
					svcUser.findTenantUser(EOSSystemConstants.LOGIN_ANONYMOUS,
							EOSSystemConstants.ADMIN_TENANT));
		} catch (EOSNotFoundException e) {
			// Should never happens
			log.debug("Anonymous user not found");
			throw e;
		}
	}

	private final SessionContext createSessionContext(final String sessionId,
			final Long tenantId, final EOSUser user) {
		final SessionContext context = new SessionContext(
				svcTenant.findTenant(tenantId), user);
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
			throw new EOSNotFoundException("Session not found: " + sessionId);
		} else {
			// return current session
			return session;
		}
	}

	/**
	 * @see com.eos.security.api.service.EOSSecurityService#setupSession(java.lang.String)
	 */
	@Override
	public void setupSession(String sessionId) throws EOSNotFoundException {
		SessionContext context = getSessionContext(sessionId);
		final EOSSession session = EOSSession.getContext();
		// Set current session
		session.setSessionId(sessionId).setSession(context);
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
	 * @see com.eos.security.api.service.EOSSecurityService#runAs(java.lang.String,
	 *      java.lang.Long, java.lang.Runnable)
	 */
	@Override
	public void runAs(String login, Long tenantId, Runnable job)
			throws EOSForbiddenException, EOSException {
		// TODO Auto-generated method stub
		final EOSSession session = EOSSession.getContext();
		final String currentSessionId = session.getSessionId();
		final SessionContext currentContext = session.getSession();
		final UUID uuid = UUID.randomUUID();
		final EOSUser user = svcUser.findTenantUser(
				EOSSystemConstants.LOGIN_SYSTEM_USER,
				EOSSystemConstants.ADMIN_TENANT);

		if (user.getType() != EOSUserType.SYSTEM) {
			throw new EOSForbiddenException("User not a system user");
		}
		// Setup session
		SessionContext context = createSessionContext(uuid.toString(),
				tenantId, user);

		try {
			session.setSessionId(uuid.toString()).setSession(context);
			job.run();
		} catch (Throwable e) {
			throw new EOSException("Failed to execute task", e);
		} finally {
			// Restore session
			session.setSessionId(currentSessionId).setSession(currentContext);
		}
	}
	
	/**
	 * @see com.eos.security.api.service.EOSSecurityService#checkPermissions(boolean, boolean, java.lang.String[])
	 */
	@Override
	public void checkPermissions(boolean verifyLoggedUser,
			boolean verifyHierarchical, String... permissions)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub
		
	}

}
