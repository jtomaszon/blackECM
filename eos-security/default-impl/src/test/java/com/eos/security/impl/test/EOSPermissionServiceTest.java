/**
 * 
 */
package com.eos.security.impl.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSPermissionService;
import com.eos.security.api.service.EOSRoleService;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.impl.test.util.EOSTestUtil;

/**
 * Test class for Permission Service.
 * 
 * @author santos.fabiano
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class EOSPermissionServiceTest {

	@Autowired
	private EOSSecurityService svcSecurity;
	@Autowired
	private EOSRoleService svcRole;
	@Autowired
	private EOSPermissionService svcPermission;

	@Before
	public void setUp() throws EOSException {
		EOSTestUtil.setup(svcSecurity);
	}

	@Test
	public void testAddListPermissions() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSRole role = EOSTestUtil.createRole("addListPermissions", svcRole);
		List<String> permissions = Arrays.asList("addListPermissions1",
				"addListPermissions2");
		svcPermission.addRolePermissions(role.getCode(), permissions);
		List<String> permList = svcPermission.listRolePermissions(
				role.getCode(), 5, 0);
		Assert.assertEquals("Add / List Permissions: size", 2, permList.size());
		Assert.assertTrue("Add / List Permissions: contains",
				permissions.contains(permList.get(0)));
		Assert.assertTrue("Add / List Permissions: contains",
				permissions.contains(permList.get(1)));
	}

	@Test
	public void testRemovePermissions() throws EOSForbiddenException,
			EOSUnauthorizedException, EOSDuplicatedEntryException {
		EOSRole role = EOSTestUtil.createRole("removePermissions", svcRole);
		List<String> permissions = Arrays.asList("removePermissions1",
				"removePermissions2", "removePermissions3");
		svcPermission.addRolePermissions(role.getCode(), permissions);
		List<String> permList = svcPermission.listRolePermissions(
				role.getCode(), 5, 0);
		Assert.assertEquals("Remove Permissions: size", 3, permList.size());
		// Remove and test it
		permissions = Arrays.asList("removePermissions2", "removePermissions3");
		svcPermission.removeRolePermission(role.getCode(), permissions);
		permList = svcPermission.listRolePermissions(role.getCode(), 5, 0);
		Assert.assertEquals("Remove Permissions: size", 1, permList.size());
		Assert.assertEquals("Remove Permissions: contains",
				"removePermissions1", permList.get(0));
	}
}
