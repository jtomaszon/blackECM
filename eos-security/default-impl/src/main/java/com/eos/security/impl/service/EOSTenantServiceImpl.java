/**
 * 
 */
package com.eos.security.impl.service;

import java.util.ArrayList;
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
import com.eos.common.util.StringUtil;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSTenantService;
import com.eos.security.api.vo.EOSTenant;
import com.eos.security.impl.dao.EOSTenantDAO;
import com.eos.security.impl.dao.EOSTenantDataDAO;
import com.eos.security.impl.model.EOSTenantDataEntity;
import com.eos.security.impl.model.EOSTenantEntity;

/**
 * @author santos.fabiano
 * 
 */
@Service
public class EOSTenantServiceImpl implements EOSTenantService {

	@Autowired
	private EOSTenantDAO tenantDAO;
	@Autowired
	private EOSTenantDataDAO tenantDataDAO;

	private static final Logger log = LoggerFactory
			.getLogger(EOSTenantServiceImpl.class);

	/**
	 * @see com.eos.security.api.service.EOSTenantService#createTenant(EOSTenant,
	 *      Map)
	 */
	@Override
	@Transactional
	public EOSTenant createTenant(EOSTenant tenant, Map<String, String> data)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSTenantEntity entity = new EOSTenantEntity();

		entity.setName(tenant.getName());
		entity.setDescription(tenant.getDescription());
		// Only set state if not null
		if (tenant.getState() != null) {
			entity.setState(tenant.getState());
		}

		tenantDAO.persist(entity);
		// Create meta data
		addTenantData(entity.getId(), data);
		tenantDAO.getEntityManager().flush();
		tenant.setId(entity.getId());
		// TODO messaging and validations, security check
		log.debug("Tenant created: " + tenant.toString());
		return tenant;
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#findTenant(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public EOSTenant findTenant(Long tenantId) {
		// TODO cache
		return entityToVO(tenantDAO.find(tenantId));
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#findTenants(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSTenant> findTenants(List<Long> tenantIds) {
		// TODO cache
		List<EOSTenantEntity> entities = tenantDAO.findTenants(tenantIds);
		List<EOSTenant> tenants = new ArrayList<>(entities.size());

		for (EOSTenantEntity entity : entities) {
			tenants.add(entityToVO(entity));
		}

		return tenants;
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#listTenants(java.util.List,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EOSTenant> listTenants(List<EOSState> states, int limit,
			int offset) {
		List<EOSTenantEntity> entities = tenantDAO.listTenants(states, limit,
				offset);
		List<EOSTenant> tenants = new ArrayList<>(entities.size());

		for (EOSTenantEntity entity : entities) {
			tenants.add(entityToVO(entity));
		}

		return tenants;
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#updateTenant(com.eos.security.api.vo.EOSTenant)
	 */
	@Override
	@Transactional
	public void updateTenant(EOSTenant tenant) throws EOSForbiddenException,
			EOSUnauthorizedException {
		// DO a find, then update, so hibernate listeners are fired.
		EOSTenantEntity entity = tenantDAO.find(tenant.getId());

		entity.setName(tenant.getName());
		entity.setDescription(tenant.getDescription());
		tenantDAO.merge(entity);
		log.debug("Tenant updated: " + tenant.toString());
		// TODO messaging and validations, security check
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#updateTenantState(java.lang.Long,
	 *      com.eos.common.EOSState)
	 */
	@Override
	@Transactional
	public void updateTenantState(Long tenantId, EOSState state)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// DO a find, then update, so hibernate listeners are fired.
		EOSTenantEntity entity = tenantDAO.find(tenantId);

		entity.setState(state);
		tenantDAO.merge(entity);
		log.debug("Tenant state updated to " + state.name());
		// TODO messaging and validations, security check
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#purgeTenant(java.lang.Long)
	 */
	@Override
	@Transactional
	public void purgeTenant(Long tenantId) throws EOSForbiddenException,
			EOSUnauthorizedException {
		// TODO Auto-generated method stub

	}

	private EOSTenant entityToVO(EOSTenantEntity entity) {
		if (entity == null) {
			return null;
		}

		EOSTenant tenant = new EOSTenant();
		tenant.setId(entity.getId());
		tenant.setName(entity.getName());
		tenant.setDescription(entity.getDescription());
		tenant.setState(entity.getState());

		return tenant;
	}

	// Tenant Data

	/**
	 * @see com.eos.security.api.service.EOSTenantService#updateTenantData(java.lang.Long,
	 *      java.util.Map)
	 */
	@Override
	@Transactional
	public void updateTenantData(Long tenantId, Map<String, String> tenantData)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO security check
		List<String> keys = new ArrayList<>(tenantData.size());
		keys.addAll(tenantData.keySet());
		// Look for data that already exists
		Map<String, String> dataFound = listTenantData(tenantId, keys);
		List<String> remove = new ArrayList<>();

		// Updates
		log.debug("Starting Tenant data update ");
		for (Entry<String, String> entry : dataFound.entrySet()) {
			// Add removes to removal list
			if (StringUtil.isEmpty(tenantData.get(entry.getKey()))) {
				remove.add(entry.getKey());
				log.debug("Tenant data set for removal: " + entry.getKey());
			} else {
				// Update
				tenantDataDAO.updateTenantData(tenantId, entry.getKey(),
						entry.getValue());
				log.debug("Tenant data [" + entry.getKey() + "] updated");
			}
			// Remove key pair value from tenantData map
			tenantData.remove(entry.getKey());
		}

		// Add new data
		addTenantData(tenantId, tenantData);
		// Remove removal list
		if (!remove.isEmpty()) {
			log.debug("Starting Tenant data removal ");
			tenantDataDAO.deleteTenantData(tenantId, remove);
		}

		// TODO Remove tenant data cache using keys variable
	}

	/**
	 * Add new tenant data.
	 * 
	 * @param tenantId
	 *            The tenant id.
	 * @param tenantData
	 *            Data map to be added.
	 */
	private void addTenantData(Long tenantId, Map<String, String> tenantData) {
		// No tenant data, do nothing
		if (tenantData == null || tenantData.isEmpty()) {
			return;
		}

		for (Entry<String, String> data : tenantData.entrySet()) {
			// Skip empty keys or values
			if (StringUtil.isEmpty(data.getKey())
					|| StringUtil.isEmpty(data.getValue())) {
				continue;
			}
			EOSTenantDataEntity entity = new EOSTenantDataEntity();
			entity.setTenantId(tenantId);
			entity.setKey(data.getKey());
			entity.setValue(data.getValue());
			tenantDataDAO.persist(entity);
		}
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#findTenantData(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findTenantData(Long tenantId, String key)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO security check
		return tenantDataDAO.findTenantDataValue(tenantId, key);
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#listTenantData(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> listTenantData(Long tenantId, List<String> keys)
			throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO security check
		List<EOSTenantDataEntity> datas = tenantDataDAO.findTenantDataValues(
				tenantId, keys);
		return dataEntityToMap(datas);
	}

	/**
	 * @see com.eos.security.api.service.EOSTenantService#listTenantData(java.lang.Long,
	 *      int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> listTenantData(Long tenantId, int limit,
			int offset) throws EOSForbiddenException, EOSUnauthorizedException {
		// TODO security check
		List<EOSTenantDataEntity> datas = tenantDataDAO.listTenantData(
				tenantId, limit, offset);
		return dataEntityToMap(datas);
	}

	/**
	 * Converts {@link EOSTenantDataEntity} list to a map.
	 * 
	 * @param datas
	 *            Data entity list.
	 * @return Map of tenant data.
	 */
	private Map<String, String> dataEntityToMap(List<EOSTenantDataEntity> datas) {
		Map<String, String> dataMap = new HashMap<>(datas.size());

		for (EOSTenantDataEntity data : datas) {
			dataMap.put(data.getKey(), data.getValue());
		}

		return dataMap;
	}

}
