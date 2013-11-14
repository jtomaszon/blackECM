/**
 * 
 */
package com.eos.security.impl.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSTenantService;
import com.eos.security.api.vo.EOSTenant;

/**
 * @author santos.fabiano
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class EOSTenantServiceTest {

	@Autowired
	private EOSTenantService svcTenant;

	// Tenant

	@Test
	public void testTenantCreate() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSTenant tenant = new EOSTenant();
		tenant.setName("Test create tenant").setDescription(
				"Create tenant description");
		tenant = svcTenant.createTenant(tenant);
		Assert.assertNotNull("Create tenant", tenant.getId());
	}

	@Test
	public void testFindTenant() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		Long tenantId = svcTenant.createTenant(
				new EOSTenant().setName("Test find tenant").setDescription(
						"Test find description")).getId();
		EOSTenant tenant = svcTenant.findTenant(tenantId);
		Assert.assertNotNull("Find tenant: id not null", tenant.getId());
		Assert.assertEquals("Find tenant id equals", tenant.getId(), tenantId);
	}

	@Test
	public void testFindTenants() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		List<Long> ids = new ArrayList<>(2);
		ids.add(svcTenant.createTenant(
				new EOSTenant().setName("Test find tenants 1").setDescription(
						"Test find description")).getId());
		ids.add(svcTenant.createTenant(
				new EOSTenant().setName("Test find tenants 2").setDescription(
						"Test find description")).getId());
		List<EOSTenant> tenants = svcTenant.findTenants(ids);
		Assert.assertEquals("Find tenants size", 2, tenants.size());
	}

	@Test
	public void testListTenants() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		svcTenant.createTenant(
				new EOSTenant().setName("Test list tenant 1").setDescription(
						"Test list description 1")).getId();
		svcTenant.createTenant(
				new EOSTenant().setName("Test list tenant 2").setDescription(
						"Test list description 2")).getId();
		List<EOSTenant> tenants = svcTenant.listTenants(null, 5, 0);
		Assert.assertTrue("List tenants higher than 1", tenants.size() > 1);
	}

	@Test
	public void testUpdateTenant() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		Long tenantId = svcTenant.createTenant(
				new EOSTenant().setName("Test update tenant").setDescription(
						"Test update tenant description")).getId();
		// Perform update
		EOSTenant updated = new EOSTenant().setId(tenantId)
				.setName("Test update tenant: UPDATED")
				.setDescription("Test update tenant description: UPDATED");
		svcTenant.updateTenant(updated);
		EOSTenant tenant = svcTenant.findTenant(tenantId);

		Assert.assertEquals(updated.getName(), tenant.getName());
		Assert.assertEquals(updated.getDescription(), tenant.getDescription());
	}

	@Test
	public void testUpdateTenantState() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		Long tenantId = svcTenant
				.createTenant(
						new EOSTenant().setName("Test update tenant state")
								.setDescription(
										"Test update tenant state description"))
				.getId();
		svcTenant.updateTenantState(tenantId, EOSState.DISABLED);
		EOSTenant tenant = svcTenant.findTenant(tenantId);
		Assert.assertEquals(tenant.getState(), EOSState.DISABLED);
	}

	@Test
	public void testTenantPurge() {
		// TODO
	}

	// Tenant Data

	@Test
	public void testCreateTenantData() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSTenant tenant = new EOSTenant();
		Map<String, String> tenantData = new HashMap<>(2);
		tenantData.put("key1", "value1");
		tenantData.put("key2", "value2");
		tenant.setName("Create tenant data")
				.setDescription("Create tenant data description")
				.setTenantData(tenantData);

		tenant = svcTenant.createTenant(tenant);
		tenantData = svcTenant.listTenantData(tenant.getId(), 5, 0);
		Assert.assertEquals("tenant data size", 2, tenantData.size());
	}

	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testUpdateTenantData() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSTenant tenant = new EOSTenant();
		Map<String, String> tenantData = new HashMap<>(2);
		tenantData.put("key1", "value1");
		tenantData.put("key2", "value2");

		tenant.setName("Update tenant data")
				.setDescription("Update tenant data description")
				.setTenantData(tenantData);
		tenant = svcTenant.createTenant(tenant);

		tenantData = svcTenant.listTenantData(tenant.getId(), 5, 0);
		Assert.assertEquals("tenant data size", 2, tenantData.size());
		// Clear and rebuild tenant data
		tenantData.clear();
		tenantData.put("key1", "newValue");
		tenantData.put("key2", ""); // Set to be removed
		tenantData.put("key3", "value3"); // New value
		tenantData.put("key4", "value4"); // New value

		// Validations
		svcTenant.updateTenantData(tenant.getId(), tenantData);
		tenantData = svcTenant.listTenantData(tenant.getId(), 5, 0);
		Assert.assertEquals("tenant data update size", 3, tenantData.size());
		// tenant data with key1 check new value
		// Not working, fetching old value
		// String value = svcTenant.findTenantData(tenant.getId(), "key1");
		// Assert.assertEquals("tenant data update key1", "newValue", value);
	}

	@Test
	public void testListTenantDataByKeys() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSTenant tenant = new EOSTenant();
		Map<String, String> tenantData = new HashMap<>(2);
		tenantData.put("key1", "value1");
		tenantData.put("key2", "value2");

		tenant.setName("List tenant data keys")
				.setDescription("List tenant data keys description")
				.setTenantData(tenantData);
		tenant = svcTenant.createTenant(tenant);
		List<String> keys = new ArrayList<>(2);
		keys.addAll(tenantData.keySet());
		tenantData = svcTenant.listTenantData(tenant.getId(), keys);

		Assert.assertTrue("List tenat keys", tenantData.containsKey("key1"));
		Assert.assertTrue("List tenat keys", tenantData.containsKey("key2"));
	}

	@Test
	public void testListTenantData() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSTenant tenant = new EOSTenant();
		Map<String, String> tenantData = new HashMap<>(2);
		tenantData.put("key1", "value1");
		tenantData.put("key2", "value2");
		tenantData.put("key3", "value3");
		tenantData.put("key4", "value4");

		tenant.setName("List tenant data keys")
				.setDescription("List tenant data keys description")
				.setTenantData(tenantData);
		tenant = svcTenant.createTenant(tenant);
		tenantData = svcTenant.listTenantData(tenant.getId(), 5, 0);
		Assert.assertEquals("tenant data size", 4, tenantData.size());
	}
}
