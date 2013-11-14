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
import com.eos.security.impl.model.EOSRoleEntity;

/**
 * Role entity DAO.
 * 
 * @author santos.fabiano
 * 
 */
@Repository
public class EOSRoleDAO extends AbstractDAO<EOSRoleEntity> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EOSRoleDAO() {
		super(EOSRoleEntity.class);
	}

	/**
	 * @see com.eos.commons.jpa.AbstractDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public EOSRoleEntity findByCode(String code, Long tenantId) {
		try {
			return em
					.createNamedQuery(EOSRoleEntity.QUERY_FIND,
							EOSRoleEntity.class)
					.setParameter(EOSRoleEntity.PARAM_CODE, code)
					.setParameter(EOSRoleEntity.PARAM_TENANT, tenantId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<EOSRoleEntity> findByCodes(List<String> codes, Long tenantId) {
		return em
				.createNamedQuery(EOSRoleEntity.QUERY_FIND, EOSRoleEntity.class)
				.setParameter(EOSRoleEntity.PARAM_CODE, codes)
				.setParameter(EOSRoleEntity.PARAM_TENANT, tenantId)
				.getResultList();
	}

}
