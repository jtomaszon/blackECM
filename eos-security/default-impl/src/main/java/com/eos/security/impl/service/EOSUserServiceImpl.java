/**
 * 
 */
package com.eos.security.impl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.impl.dao.EOSUserDAO;
import com.eos.security.impl.dao.EOSUserTenantDAO;
import com.eos.security.impl.model.EOSUserEntity;
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

	private EOSUserDAO userDAO;
	private EOSUserTenantDAO userTenantDAO;

	@Autowired
	public void setUserDAO(EOSUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	public void setUserTenantDAO(EOSUserTenantDAO userTenantDAO) {
		this.userTenantDAO = userTenantDAO;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#createUser(com.eos.security.api.vo.EOSUser)
	 */
	@Override
	@Transactional
	public EOSUser createUser(EOSUser user) throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		// TODO Validations and security
		EOSUserEntity entity = userDAO.checkedFind(user.getLogin());

		if (entity != null) {
			throw new EOSDuplicatedEntryException("User with login "
					+ user.getLogin() + " already exists");
		}

		entity = new EOSUserEntity();
		entity.setLogin(user.getLogin()).setEmail(user.getPersonalMail())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());
		// Create user
		userDAO.persist(entity);
		EOSUserTenantEntity userTenant = addUserToTenant(user.getLogin(),
				user.getNickName(), user.getEmail(), user.getState());

		// Override state and tenant
		user.setState(userTenant.getState()).setTenantId(
				userTenant.getTenantId());
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
	public EOSUser findUser(String login) {
		return findTenantUser(login, SessionContextManager.getCurrentTenantId());
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findTenantUser(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public EOSUser findTenantUser(String login, Long tenantId) {
		// TODO Validations and security
		return entityToVo(userTenantDAO.findByLogin(login, tenantId));
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findUsers(java.util.List)
	 */
	@Override
	public List<EOSUser> findUsers(List<String> logins) {
		// TODO Validations and security
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
	public List<EOSUser> listUsers(List<EOSState> states, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#updateUser(com.eos.security.api.vo.EOSUser)
	 */
	@Override
	public void updateUser(EOSUser user) throws EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#deleteUser(java.lang.String)
	 */
	@Override
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
				.setTenantId(entity.getTenantId());

	}

	// User Data

	/**
	 * @see com.eos.security.api.service.EOSUserService#updateUserData(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public void updateUserData(String login, Map<String, String> userData)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#removeUserData(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void removeUserData(String login, List<String> keys)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#findUserData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String findUserData(String login, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#listUserData(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public Map<String, String> listUserData(String login, List<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eos.security.api.service.EOSUserService#listUserData(java.lang.String,
	 *      int, int)
	 */
	@Override
	public Map<String, String> listUserData(String login, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
