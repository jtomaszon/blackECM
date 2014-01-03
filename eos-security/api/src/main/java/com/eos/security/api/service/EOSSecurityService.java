/**
 * 
 */
package com.eos.security.api.service;

import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
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
	 * @throws EOSException
	 *             If any error happens.
	 */
	public SessionContext createSessionContext(String sessionId, Long tenantId)
			throws EOSException;

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
	 * Setup current session information for the given session ID.
	 * 
	 * @param sessionId
	 *            The session ID.
	 * @throws EOSNotFoundException
	 *             If no session with the given id was found.
	 */
	public void setupSession(String sessionId) throws EOSNotFoundException;

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
	 * Executes a job with the given user into the given tenant. Only system
	 * users can execute jobs.
	 * 
	 * @param login
	 *            Login of a system user.
	 * @param tenantId
	 *            Id of the tenant for the job.
	 * @param job
	 *            Task job.
	 * @throws EOSException
	 * @throws EOSForbiddenException
	 */
	public void runAs(String login, Long tenantId, Runnable job)
			throws EOSForbiddenException, EOSException;

	/**
	 * Performs the following validations:
	 * <ul>
	 * <li>Check if the user has any of the given permissions, if not throws
	 * EOSForbiddenException.</li>
	 * <li>If the parameter verifyLoggedUser is true, checks if the user is
	 * logged, if not throws EOSUnauthorizedException.</li>
	 * <li>If the parameter verifyHierarchical, before validate permissions,
	 * verify if the user has tenant administration permission or super
	 * administrator permission.</li>
	 * <ul>
	 * 
	 * <p>
	 * The validation or is:
	 * </p>
	 * <ul>
	 * <li>Verify user logged.</li>
	 * <li>Verify hierarchical permissions.</li>
	 * <li>Verify permissions.</li>
	 * <ul>
	 * 
	 * @param verifyLoggedUser
	 *            Verify if the user is logged.
	 * @param verifyHierarchical
	 *            Verify hierarchical permissions, as tenant administrator and
	 *            super administrator.
	 * @param permissions
	 *            List of permissions to be checked.
	 * @throws EOSForbiddenException
	 *             If the user do not have any of the given permissions or any
	 *             of the hierarchical permissions.
	 * @throws EOSUnauthorizedException
	 *             If the user is not logged.
	 */
	public void checkPermissions(boolean verifyLoggedUser,
			boolean verifyHierarchical, String... permissions)
			throws EOSForbiddenException, EOSUnauthorizedException;

}
