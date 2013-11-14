/**
 * 
 */
package com.eos.security.impl.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.eos.commons.jpa.AbstractDAO;
import com.eos.security.impl.model.EOSPermissionEntity;

/**
 * Role Permission DAO.
 * 
 * @author santos.fabiano
 * 
 */
@Repository
public class EOSPermissionDAO extends AbstractDAO<EOSPermissionEntity> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EOSPermissionDAO() {
		super(EOSPermissionEntity.class);
	}

	/**
	 * @see com.eos.commons.jpa.AbstractDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
