/**
 * 
 */
package com.eos.security.impl.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eos.common.EOSLevel;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSGroupService;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.impl.dao.EOSGroupDAO;
import com.eos.security.impl.model.EOSGroupEntity;
import com.eos.security.impl.session.SessionContextManager;

/**
 * Default Group Service implementation.
 * 
 * @author santos.fabiano
 * 
 */
@Service
public class EOSGroupServiceImpl implements EOSGroupService {

	private static final Logger log = LoggerFactory
			.getLogger(EOSGroupServiceImpl.class);

	private EOSGroupDAO groupDAO;

	@Autowired
	public void setGroupDAO(EOSGroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#createGroup(com.eos.security.api.vo.EOSGroup)
	 */
	@Override
	@Transactional
	public EOSGroup createGroup(EOSGroup group)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Security, validations and messaging
		EOSGroupEntity entity = new EOSGroupEntity();
		entity.setName(group.getName()).setDescription(group.getDescription())
				.setLevel(group.getLevel());

		groupDAO.persist(entity);
		group.setId(entity.getId()).setTenantId(entity.getTenantId());

		log.debug("Group created: " + entity.toString());
		return group;
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#findGroup(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public EOSGroup findGroup(Long groupId) throws EOSForbiddenException {
		// TODO Security and cache
		return entityToVo(groupDAO.find(groupId,
				SessionContextManager.getCurrentTenantId()));
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#findGroups(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSGroup> findGroups(List<Long> groups)
			throws EOSForbiddenException {
		// TODO Security and cache
		List<EOSGroupEntity> entities = groupDAO.find(groups,
				SessionContextManager.getCurrentTenantId());
		List<EOSGroup> groupList = new ArrayList<>(entities.size());

		for (EOSGroupEntity entity : entities) {
			groupList.add(entityToVo(entity));
		}

		return groupList;
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#updateGroup(com.eos.security.api.vo.EOSGroup)
	 */
	@Override
	@Transactional
	public void updateGroup(EOSGroup group) throws EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Security, validations and messaging
		EOSGroupEntity entity = groupDAO.find(group.getId(),
				SessionContextManager.getCurrentTenantId());
		entity.setName(group.getName()).setDescription(group.getDescription())
				.setLevel(group.getLevel());

		groupDAO.merge(entity);
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#deleteGroup(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteGroup(Long groupId) throws EOSForbiddenException,
			EOSUnauthorizedException, EOSNotFoundException {
		// TODO Security, validations and messaging
		// Find required for group level validation
		EOSGroupEntity entity = groupDAO.find(groupId,
				SessionContextManager.getCurrentTenantId());

		if (entity == null) {
			throw new EOSNotFoundException("Group not found for ID: " + groupId);
		}

		log.debug("Group removed: " + entity.toString());
		groupDAO.remove(entity);
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#listGroups(java.lang.Integer,
	 *      java.lang.Integer, int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSGroup> listGroups(Integer minimumLevel,
			Integer maximumLevel, int limit, int offset)
			throws EOSForbiddenException {
		// TODO Security, validations and messaging

		if (minimumLevel == null) {
			minimumLevel = EOSLevel.PUBLIC.getLevel();
		}

		if (maximumLevel == null) {
			maximumLevel = EOSLevel.MAXIMUM.getLevel();
		}

		List<EOSGroupEntity> entities = groupDAO.list(minimumLevel,
				maximumLevel, limit, offset,
				SessionContextManager.getCurrentTenantId());
		List<EOSGroup> groups = new ArrayList<>(entities.size());

		for (EOSGroupEntity entity : entities) {
			groups.add(entityToVo(entity));
		}

		return groups;
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#addUsersToGroup(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void addUsersToGroup(Long groupId, List<String> users)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#removeUsersFromGroup(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void removeUsersFromGroup(Long groupId, List<String> users)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#listGroupUsers(java.lang.Long,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSUser> listGroupUsers(Long groupId, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#addRolesToGroup(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void addRolesToGroup(Long groupId, List<String> roles)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#removeRolesFromGroup(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void removeRolesFromGroup(Long groupId, List<String> roles)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSGroupService#listGroupRoles(java.lang.Long,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSRole> listGroupRoles(Long groupId, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	private EOSGroup entityToVo(EOSGroupEntity entity) {
		if (entity == null) {
			return null;
		}

		return new EOSGroup().setId(entity.getId()).setName(entity.getName())
				.setDescription(entity.getDescription())
				.setLevel(entity.getLevel()).setTenantId(entity.getTenantId());
	}

}
