/**
 * 
 */
package com.eos.commons.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * @author santos.fabiano
 * 
 */
public abstract class AbstractDAO<T> {

	private Class<T> entityClazz;

	/**
	 * Default constructor.
	 * 
	 * @param entityClazz
	 */
	protected AbstractDAO(Class<T> entityClazz) {
		this.entityClazz = entityClazz;
	}

	/**
	 * Find the entity referenced by this DAO with the given id.
	 * 
	 * @param id
	 *            the entity id (PK)
	 * @return entity
	 * @throws PersistenceException
	 *             If not found.
	 */
	public T find(Object id) throws PersistenceException {
		return getEntityManager().find(entityClazz, id);
	}

	/**
	 * Find the entity referenced by this DAO with the given id. Returns null if
	 * not found.
	 * 
	 * @param id
	 *            the entity id (PK)
	 * @return Entity or null if not found.
	 */
	public T checkedFind(Object id) {
		try {
			return find(id);
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Get the managed reference of the entity with the given id.
	 * 
	 * @param id
	 *            the entity id (PK)
	 * @return Entity reference.
	 * @throws PersistenceException
	 *             If not found.
	 */
	public T getReference(Object id) throws PersistenceException {
		return getEntityManager().getReference(entityClazz, id);
	}

	/**
	 * Get the managed reference of the entity with the given id. Returns null
	 * if not found.
	 * 
	 * @param id
	 *            the entity id (PK)
	 * @return Entity reference or null if not found.
	 */
	public T getCheckedReference(Object id) {
		try {
			return getReference(id);
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Persist an entity, creating a new one.
	 * 
	 * @param entity
	 */
	public void persist(T entity) {
		getEntityManager().persist(entity);
	}

	/**
	 * Updates the given entity.
	 * 
	 * @param entity
	 * @return entity
	 */
	public T merge(T entity) {
		return getEntityManager().merge(entity);
	}

	/**
	 * Deletes the given entity.
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		getEntityManager().remove(entity);
	}

	/**
	 * Get the entity manager.
	 * 
	 * @return Entity manager
	 */
	public abstract EntityManager getEntityManager();
}
