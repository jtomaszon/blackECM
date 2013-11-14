/**
 * 
 */
package com.eos.security.api.service;

import java.util.List;

import com.eos.common.EOSLevel;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;

/**
 * Role service utility, deals with role, role user relations, role group
 * relations and role permissions.
 * 
 * @author fabiano.santos
 * 
 */
public interface EOSRoleService {

	// Role

	/**
	 * Creates a new role.
	 * 
	 * @param role
	 *            The role info.
	 * @return The created role.
	 * @throws EOSDuplicatedEntryException
	 *             If the role already exists.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for role creation.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can create roles.
	 */
	public EOSRole createRole(EOSRole role) throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Updates the role info.
	 * 
	 * @param role
	 *            The role info.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for role update.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can update roles.
	 */
	public void updateRole(EOSRole role) throws EOSForbiddenException,
			EOSUnauthorizedException;

	/**
	 * Delete the given role and all its relations (Users, Groups, Permissions).
	 * 
	 * @param code
	 *            The role code.
	 * @throws EOSForbiddenException
	 *             If the user do not have permission for role deletion.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can delete roles.
	 */
	public void deleteRole(String code) throws EOSForbiddenException,
			EOSUnauthorizedException;

	/**
	 * Find a role by its code.
	 * 
	 * @param code
	 *            The role code.
	 * @return Role information.
	 * @throws EOSForbiddenException
	 *             For {@link EOSLevel#INTERNAL} special permissions are
	 *             required.
	 */
	public EOSRole findRole(String code) throws EOSForbiddenException;

	/**
	 * Find roles by their code.
	 * 
	 * @param codes
	 *            List of role codes.
	 * @return List of roles found.
	 * @throws EOSForbiddenException
	 *             For {@link EOSLevel#INTERNAL} special permissions are
	 *             required.
	 */
	public List<EOSRole> findRoles(List<String> codes)
			throws EOSForbiddenException;

	/**
	 * List roles filtered by level.
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
	 * @throws EOSUnauthorizedException
	 *             For {@link EOSLevel#INTERNAL} is required special
	 *             permissions.
	 */
	public List<EOSRole> listRoles(Integer minimumLevel, Integer maximumLevel,
			int limit, int offset) throws EOSUnauthorizedException;

	// Role User

	/**
	 * Add users to a role.
	 * 
	 * @param code
	 *            The role code to add users.
	 * @param users
	 *            List of users to be added.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to add users to a role.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void addUsersToRole(String code, List<Long> users)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove users from a role.
	 * 
	 * @param code
	 *            The role code where the users will be removed.
	 * @param users
	 *            The list of users to be removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to add users to a role.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void removeUsersFromRole(String code, List<Long> users)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * List users from a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return List of users.
	 */
	public List<EOSUser> listRoleUsers(String code, int limit, int offset);

	// Role Group

	/**
	 * Add groups to a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param groups
	 *            The list of groups to be added.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to add groups to a
	 *             role.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void addGroupsToRole(String code, List<Long> groups)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove groups from a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param groups
	 *            List of groups to be removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission to remove groups from a
	 *             role.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void removeGroupsFromRole(String code, List<Long> groups)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * List groups of a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return List of groups.
	 */
	public List<EOSGroup> listRoleGroups(String code, int limit, int offset);

	// Role Permission

	/**
	 * Add permissions to a role. If the role already has any permission no
	 * further action will performed.
	 * 
	 * @param code
	 *            The role code.
	 * @param permissions
	 *            List of permissions to be added.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for role manipulation.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void addRolePermissions(String code, List<String> permissions)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * Remove permissions from a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param permissions
	 *            List of permissions to be removed.
	 * @throws EOSForbiddenException
	 *             If the creator do not have permission for role manipulation.
	 * @throws EOSUnauthorizedException
	 *             Only authenticated users can manipulate roles.
	 */
	public void removeRolePermission(String code, List<String> permissions)
			throws EOSForbiddenException, EOSUnauthorizedException;

	/**
	 * List all permissions of a role.
	 * 
	 * @param code
	 *            The role code.
	 * @param limit
	 *            Maximum number of registers.
	 * @param offset
	 *            Initial point.
	 * @return List of permissions.
	 */
	public List<String> listRolePermissions(String code, int limit, int offset);
}
