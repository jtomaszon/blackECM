/**
 * 
 */
package com.eos.security.api.service;

import java.util.List;
import java.util.Map;

import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.session.SessionContext;

/**
 * Security functions, as access and user authorization.
 * 
 * @author fabiano.santos
 * 
 */
public interface EOSSecurityService {

	/**
	 * Create a session for the given tenant and session Id.
	 * 
	 * @param sessionId
	 *            The session ID.
	 * @param tenantId
	 *            The tenant for this session.
	 * @return Session context created.
	 */
	public SessionContext createSessionContext(String sessionId, Long tenantId);

	/**
	 * Retrieve session context information based on session Id.
	 * 
	 * @param sessionId
	 *            The session Id.
	 * @return Session context.
	 * @throws EOSNotFoundException
	 *             If no session with the given id was found.
	 */
	public SessionContext getSessionContext(String sessionId)
			throws EOSNotFoundException;

	/**
	 * Perform the user login. This service also loads the
	 * {@link SessionContext}.
	 * 
	 * @param login
	 *            The user login.
	 * @param password
	 *            The user password.
	 * @throws EOSException
	 *             If user not found or invalid password.
	 */
	public void login(String login, String password) throws EOSException;

	/**
	 * Perform the user logout, also clean up the {@link SessionContext}.
	 * 
	 * @throws EOSUnauthorizedException
	 *             If user not logged.
	 */
	public void logout() throws EOSUnauthorizedException;

	/**
	 * Verify if any user is logged.
	 * 
	 * @return True if an user is logged, false otherwise.
	 */
	public boolean isLogged();

	/**
	 * Validates the given user has a permission. Validates access through role
	 * group and user group relations.
	 * 
	 * @param login
	 *            User login.
	 * @param permission
	 *            Permission to be verified.
	 * @return True if the user has the permission, false otherwise.
	 */
	public boolean hasPermission(String login, String permission);

	/**
	 * Validates the given user has the permissions. Validates access through
	 * role group and user group relations.
	 * 
	 * @param login
	 *            User login.
	 * @param permissions
	 *            Permission list to be verified.
	 * @return Map with the permission string as key and the value indicates if
	 *         the user has or not.
	 */
	public Map<String, Boolean> hasPermissions(String login,
			List<String> permissions);

}
