/**
 * 
 */
package com.eos.security.impl.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.common.util.StringUtil;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.impl.dao.EOSUserDAO;
import com.eos.security.impl.dao.EOSUserTenantDAO;
import com.eos.security.impl.dao.EOSUserTenantDataDAO;
import com.eos.security.impl.model.EOSUserEntity;
import com.eos.security.impl.model.EOSUserTenantDataEntity;
import com.eos.security.impl.model.EOSUserTenantEntity;
import com.eos.security.impl.session.SessionContextManager;

/**
 * Default user service implementation.
 * 
 * @author santos.fabiano
 * 
 */
@Service
public class EOSUserServiceImpl implements EOSUserService {

	private static final Logger log = LoggerFactory
			.getLogger(EOSUserServiceImpl.class);

	private EOSUserDAO userDAO;
	private EOSUserTenantDAO userTenantDAO;
	private EOSUserTenantDataDAO userTenantDataDAO;

	@Autowired
	public void setUserDAO(EOSUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	public void setUserTenantDAO(EOSUserTenantDAO userTenantDAO) {
		this.userTenantDAO = userTenantDAO;
	}

	@Autowired
	public void setUserTenantDataDAO(EOSUserTenantDataDAO userTenantDataDAO) {
		this.userTenantDataDAO = userTenantDataDAO;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#createUser(com.eos.security.api.vo.EOSUser,
	 *      Map)
	 */
	@Override
	@Transactional
	public EOSUser createUser(EOSUser user, Map<String, String> userData)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Validations and security
		EOSUserEntity entity = userDAO.checkedFind(user.getLogin());

		if (entity == null) {
			log.debug("User entity not found, creating it");
			entity = new EOSUserEntity();
			entity.setLogin(user.getLogin()).setEmail(user.getPersonalMail())
					.setFirstName(user.getFirstName())
					.setLastName(user.getLastName());
			// Create user
			userDAO.persist(entity);
		}

		EOSUserTenantEntity userTenant = addUserToTenant(user.getLogin(),
				user.getNickName(), user.getEmail(), user.getState());

		// Override state and tenant
		user.setState(userTenant.getState()).setTenantId(
				userTenant.getTenantId());

		// User data
		if (userData != null && !userData.isEmpty()) {
			addUserTenantData(user.getLogin(), userData);
		}

		return user;
	}

	/**
	 * Add a user to a tenant.
	 * 
	 * @param login
	 * @param nickName
	 * @param email
	 * @param state
	 * @return The user tenant created.
	 */
	private EOSUserTenantEntity addUserToTenant(String login, String nickName,
			String email, EOSState state) {
		log.debug("Adding user " + login + " to current tenant");
		EOSUserTenantEntity entity = new EOSUserTenantEntity();
		entity.setLogin(login).setNickName(nickName).setTenantMail(email);

		if (state != null) {
			entity.setState(state);
		}

		userTenantDAO.persist(entity);
		return entity;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findUser(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public EOSUser findUser(String login) throws EOSNotFoundException {
		return findTenantUser(login, SessionContextManager.getCurrentTenantId());
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findTenantUser(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public EOSUser findTenantUser(String login, Long tenantId)
			throws EOSNotFoundException {
		// TODO Validations, cache and security
		return entityToVo(userTenantDAO.findByLogin(login, tenantId));
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findUsers(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSUser> findUsers(List<String> logins) {
		// TODO Validations, cache and security
		List<EOSUserTenantEntity> entities = userTenantDAO.findByLogins(logins,
				SessionContextManager.getCurrentTenantId());
		List<EOSUser> users = new ArrayList<>(entities.size());

		for (EOSUserTenantEntity entity : entities) {
			users.add(entityToVo(entity));
		}

		return users;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#listUsers(java.util.List,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSUser> listUsers(List<EOSState> states, int limit, int offset) {
		// TODO Validations and security
		if (states == null || states.isEmpty()) {
			states = Arrays.asList(EOSState.values());
		}

		List<EOSUserTenantEntity> entities = userTenantDAO.list(states,
				SessionContextManager.getCurrentTenantId(), limit, offset);
		List<EOSUser> users = new ArrayList<>(entities.size());

		for (EOSUserTenantEntity entity : entities) {
			users.add(entityToVo(entity));
		}

		return users;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#updateUser(com.eos.security.api.vo.EOSUser)
	 */
	@Override
	@Transactional
	public void updateUser(EOSUser user) throws EOSForbiddenException,
			EOSUnauthorizedException, EOSNotFoundException {
		// TODO Validations and security
		EOSUserTenantEntity entity = userTenantDAO.findByLogin(user.getLogin(),
				SessionContextManager.getCurrentTenantId());
		// Find UserEntity, because the attached one in UserTenant isn't managed
		EOSUserEntity userEntity = userDAO.checkedFind(user.getLogin());

		if (entity == null || userEntity == null) {
			throw new EOSNotFoundException("User not found with login: "
					+ user.getLogin());
		}

		// UserTenantEntity
		entity.setNickName(user.getNickName()).setTenantMail(user.getEmail());
		// UserEntity
		userEntity.setEmail(user.getPersonalMail())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());

		userDAO.merge(userEntity);
		userTenantDAO.merge(entity);
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#deleteUser(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteUser(String login) throws EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	private EOSUser entityToVo(EOSUserTenantEntity entity) {
		if (entity == null) {
			return null;
		}

		return new EOSUser().setLogin(entity.getLogin())
				.setFirstName(entity.getUser().getFirstName())
				.setLastName(entity.getUser().getLastName())
				.setPersonalMail(entity.getUser().getEmail())
				.setNickName(entity.getNickName())
				.setEmail(entity.getTenantMail()).setState(entity.getState())
				.setType(entity.getUser().getType())
				.setTenantId(entity.getTenantId());

	}

	// User Data

	/**
	 * @see com.eos.security.api.service.EOSUserService#updateUserData(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	@Transactional
	public void updateUserData(String login, Map<String, String> userData)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO security check
		final Long tenantId = SessionContextManager.getCurrentTenantId();
		List<String> keys = new ArrayList<>(userData.size());
		keys.addAll(userData.keySet());
		// Look for data that already exists
		Map<String, String> dataFound = listUserData(login, keys);
		List<String> remove = new ArrayList<>();

		// Updates
		log.debug("Starting User Tenant data update ");
		for (Entry<String, String> entry : dataFound.entrySet()) {
			// Add removes to removal list
			if (StringUtil.isEmpty(userData.get(entry.getKey()))) {
				remove.add(entry.getKey());
				log.debug("User Tenant data set for removal: " + entry.getKey());
			} else {
				// Update
				userTenantDataDAO.updateUserData(login, entry.getKey(),
						entry.getValue(), tenantId);
				log.debug("User Tenant data [" + entry.getKey() + "] updated");
			}
			// Remove key pair value from tenantData map
			userData.remove(entry.getKey());
		}

		// Add new data
		addUserTenantData(login, userData);
		// Remove removal list
		if (!remove.isEmpty()) {
			log.debug("Starting Tenant data removal ");
			userTenantDataDAO.deleteUserData(login, remove, tenantId);
		}

		// TODO Remove user tenant data cache using keys variable
	}

	private void addUserTenantData(String login, Map<String, String> userData) {
		log.debug("Adding user data to user " + login);
		
		for (Entry<String, String> entry : userData.entrySet()) {
			EOSUserTenantDataEntity entity = new EOSUserTenantDataEntity()
					.setLogin(login).setKey(entry.getKey())
					.setValue(entry.getValue());
			userTenantDataDAO.persist(entity);
		}
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findUserData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findUserData(String login, String key) {
		// TODO security check
		return userTenantDataDAO.findUserData(login, key,
				SessionContextManager.getCurrentTenantId());
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#listUserData(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> listUserData(String login, List<String> keys) {
		// TODO security check
		return entitiesToMap(userTenantDataDAO.listUserData(login, keys,
				SessionContextManager.getCurrentTenantId()));
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#listUserData(java.lang.String,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> listUserData(String login, int limit, int offset) {
		// TODO security check
		return entitiesToMap(userTenantDataDAO.listUserData(login, limit,
				offset, SessionContextManager.getCurrentTenantId()));
	}

	private Map<String, String> entitiesToMap(
			List<EOSUserTenantDataEntity> entities) {
		Map<String, String> userData = new HashMap<>(entities.size());

		for (EOSUserTenantDataEntity entity : entities) {
			userData.put(entity.getKey(), entity.getValue());
		}

		return userData;
	}

}
