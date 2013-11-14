/**
 * 
 */
package com.eos.security.impl.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.eos.commons.jpa.AbstractDAO;
import com.eos.security.impl.model.EOSUserTenantEntity;

/**
 * User tenant DAO.
 * 
 * @author santos.fabiano
 * 
 */
@Repository
public class EOSUserTenantDAO extends AbstractDAO<EOSUserTenantEntity> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EOSUserTenantDAO() {
		super(EOSUserTenantEntity.class);
	}

	/**
	 * @see com.eos.commons.jpa.AbstractDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public EOSUserTenantEntity findByLogin(String login, Long tenantId) {
		try {
			return em
					.createNamedQuery(EOSUserTenantEntity.QUERY_FIND,
							EOSUserTenantEntity.class)
					.setParameter(EOSUserTenantEntity.PARAM_LOGIN, login)
					.setParameter(EOSUserTenantEntity.PARAM_TENANT, tenantId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<EOSUserTenantEntity> findByLogins(List<String> logins,
			Long tenantId) {
		return em
				.createNamedQuery(EOSUserTenantEntity.QUERY_FIND_MULTIPLE,
						EOSUserTenantEntity.class)
				.setParameter(EOSUserTenantEntity.PARAM_LOGIN, logins)
				.setParameter(EOSUserTenantEntity.PARAM_TENANT, tenantId)
				.getResultList();
	}

}
