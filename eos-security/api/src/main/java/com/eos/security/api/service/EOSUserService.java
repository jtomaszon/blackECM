/**
 * 
 */
package com.eos.security.api.service;

import java.util.List;
import java.util.Map;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.vo.EOSUser;

/**
 * User service utility.
 * 
 * @author santos.fabiano
 * 
 */
public interface EOSUserService {

	// User

	/**
	 * Create a new user. If the user already exists, a relation with the
	 * current tenant will be created.
	 * 
	 * @param user
	 *            User to be created.
	 * @return The user created.
	 * @throws EOSDuplicatedEntryException
	 *             If an user already exists in the current tenant.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for user creation.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can create other users.
	 */
	public EOSUser createUser(EOSUser user) throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Finds the user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return The user or null if not found.
	 */
	public EOSUser findUser(String login);

	/**
	 * Finds the user with the given login on the given tenant. Internal use
	 * only.
	 * 
	 * @param login
	 *            User login.
	 * @param tenantId
	 *            The tenant id which the user belongs.
	 * @return The user or null if not found.
	 */
	public EOSUser findTenantUser(String login, Long tenantId);

	/**
	 * Find users by their login.
	 * 
	 * @param logins
	 *            List of user logins.
	 * @return List of users found.
	 */
	public List<EOSUser> findUsers(List<String> logins);

	/**
	 * List users with the given states. Listing user not active are requires
	 * higher permission than usual.
	 * 
	 * @param states
	 *            If null, only active users are returned.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return User List of users.
	 */
	public List<EOSUser> listUsers(List<EOSState> states, int limit, int offset);

	/**
	 * Update user info.
	 * 
	 * @param user
	 *            User info.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for user update.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can create other users.
	 */
	public void updateUser(EOSUser user) throws EOSForbiddenException,
			EOSUnauthorizedException;

	/**
	 * Delete a user, the user is marked as deleted.
	 * 
	 * @param login
	 *            User login.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for user removal.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can create other users.
	 */
	public void deleteUser(String login) throws EOSForbiddenException,
			EOSUnauthorizedException;

	// User Data

	/**
	 * Add user data to the given user. If the data value exists, then an update
	 * will be performed. If value is null, then the key, value pair will be
	 * removed.
	 * 
	 * @param login
	 *            User login.
	 * @param userData
	 *            Data to be updated, added or removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for user data update.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can update user data.
	 */
	public void updateUserData(String login, Map<String, String> userData)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove the user data with the given keys.
	 * 
	 * @param login
	 *            User login.
	 * @param keys
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for user data removal.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can remove user data.
	 */
	public void removeUserData(String login, List<String> keys)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Find a user data that has the given key.
	 * 
	 * @param login
	 *            User login.
	 * @param key
	 *            User data key.
	 * @return User data value.
	 */
	public String findUserData(String login, String key);

	/**
	 * List user data with the given keys.
	 * 
	 * @param login
	 *            User login.
	 * @param keys
	 *            List of keys to find user data.
	 * @return A map with user data.
	 */
	public Map<String, String> listUserData(String login, List<String> keys);

	/**
	 * List all user data.
	 * 
	 * @param login
	 *            User login.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return A map with user data.
	 */
	public Map<String, String> listUserData(String login, int limit, int offset);
}
