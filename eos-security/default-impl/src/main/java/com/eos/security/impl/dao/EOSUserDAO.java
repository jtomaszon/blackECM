/**
 * 
 */
package com.eos.security.impl.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.eos.commons.jpa.AbstractDAO;
import com.eos.security.impl.model.EOSUserEntity;

/**
 * User DAO.
 * 
 * @author santos.fabiano
 * 
 */
@Repository
public class EOSUserDAO extends AbstractDAO<EOSUserEntity> {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EOSUserDAO() {
		super(EOSUserEntity.class);
	}

	/**
	 * @see com.eos.commons.jpa.AbstractDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
}
