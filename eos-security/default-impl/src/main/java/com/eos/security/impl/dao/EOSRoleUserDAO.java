/**
 * 
 */
package com.eos.security.impl.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.eos.commons.jpa.AbstractDAO;
import com.eos.security.impl.model.EOSRoleUserEntity;

/**
 * Role User entity DAO.
 * 
 * @author santos.fabiano
 * 
 */
@Repository
public class EOSRoleUserDAO extends AbstractDAO<EOSRoleUserEntity> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EOSRoleUserDAO() {
		super(EOSRoleUserEntity.class);
	}

	/**
	 * @see com.eos.commons.jpa.AbstractDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public EOSRoleUserEntity findByUserAndRole(Long userId, Long roleId,
			Long tenantId) {
		try {
			return em
					.createNamedQuery(EOSRoleUserEntity.QUERY_FIND,
							EOSRoleUserEntity.class)
					.setParameter(EOSRoleUserEntity.PARAM_USER, userId)
					.setParameter(EOSRoleUserEntity.PARAM_ROLE, roleId)
					.setParameter(EOSRoleUserEntity.PARAM_TENANT, tenantId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
