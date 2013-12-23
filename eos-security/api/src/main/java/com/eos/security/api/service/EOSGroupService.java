/**
 * 
 */
package com.eos.security.api.service;

import java.util.List;

import com.eos.common.EOSLevel;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;

/**
 * Group service utility, deals with Group and Group User relations.
 * 
 * @author fabiano.santos
 * 
 */
public interface EOSGroupService {

	// Group

	/**
	 * Create a new group.
	 * 
	 * @param group
	 *            Group info.
	 * @return The group created.
	 * @throws EOSDuplicatedEntryException
	 *             If the group already exists.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for group creation.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can create groups.
	 */
	public EOSGroup createGroup(EOSGroup group)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException;

	/**
	 * Find a group.
	 * 
	 * @param groupId
	 *            the group Id
	 * @return Group information.
	 * @throws EOSForbiddenException
	 *             For {@link EOSLevel#INTERNAL} special permissions are
	 *             required.
	 */
	public EOSGroup findGroup(Long groupId) throws EOSForbiddenException;

	/**
	 * Find groups by its Ids.
	 * 
	 * @param groups
	 *            List of group Ids.
	 * @return List of groups.
	 * @throws EOSForbiddenException
	 *             For {@link EOSLevel#INTERNAL} special permissions are
	 *             required.
	 */
	public List<EOSGroup> findGroups(List<Long> groups)
			throws EOSForbiddenException;

	/**
	 * Update a group info.
	 * 
	 * @param group
	 *            Group info.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for group update.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can update groups.
	 */
	public void updateGroup(EOSGroup group) throws EOSForbiddenException,
			EOSUnauthorizedException;

	/**
	 * Deletes a group. All group user relation and role group relations are
	 * also deleted.
	 * 
	 * @param groupId
	 *            The group Id to be deleted.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for group deletion.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can delete groups.
	 * @throws EOSNotFoundException
	 *             If the group is not found.
	 */
	public void deleteGroup(Long groupId) throws EOSForbiddenException,
			EOSUnauthorizedException, EOSNotFoundException;

	/**
	 * List groups filtered by level.
	 * 
	 * @param minimumLevel
	 *            Minimum group level, if null this value will be set to
	 *            {@link EOSLevel#PUBLIC}.
	 * @param maximumLevel
	 *            Maximum group level, if null this value will be set to
	 *            {@link EOSLevel#MAXIMUM}.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return Group list.
	 * @throws EOSForbiddenException
	 *             For {@link EOSLevel#INTERNAL} special permissions are
	 *             required.
	 */
	public List<EOSGroup> listGroups(Integer minimumLevel,
			Integer maximumLevel, int limit, int offset)
			throws EOSForbiddenException;

	// Group User

	/**
	 * Add users to the given group.
	 * 
	 * @param groupId
	 *            The group id to add users.
	 * @param users
	 *            List of users member.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to add users to a
	 *             group.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate groups.
	 */
	public void addUsersToGroup(Long groupId, List<String> users)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove users from the given group.
	 * 
	 * @param groupId
	 *            The group id where the users will be removed.
	 * @param users
	 *            List of users to be removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to remove users from
	 *             group.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate groups.
	 */
	public void removeUsersFromGroup(Long groupId, List<String> users)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * List group members.
	 * 
	 * @param groupId
	 *            the group id to list users.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return List of users.
	 */
	public List<EOSUser> listGroupUsers(Long groupId, int limit, int offset);

	/**
	 * Add roles to the given group.
	 * 
	 * @param groupId
	 *            The group id where the roles will be added.
	 * @param roles
	 *            List of roles to be added.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to add roles to a
	 *             group.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate groups.
	 */
	public void addRolesToGroup(Long groupId, List<String> roles)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove roles from the given group.
	 * 
	 * @param groupId
	 *            The group id where the roles will be removed.
	 * @param roles
	 *            List of roles to be removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to remove roles from a
	 *             group.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate groups.
	 */
	public void removeRolesFromGroup(Long groupId, List<String> roles)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * List all roles from the given group.
	 * 
	 * @param groupId
	 *            The group id to list roles.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return List of roles.
	 */
	public List<EOSRole> listGroupRoles(Long groupId, int limit, int offset);
}
