/**
 * 
 */
package com.eos.security.impl.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eos.common.EOSLevel;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSGroupService;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.impl.test.util.EOSTestUtil;

/**
 * Test class for Group Service.
 * 
 * @author santos.fabiano
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class EOSGroupServiceTest {

	@Autowired
	private EOSGroupService svcGroup;
	@Autowired
	private EOSSecurityService svcSecurity;

	@Before
	public void setUp() throws EOSException {
		EOSTestUtil.setup(svcSecurity);
	}

	@Test
	public void testCreateGroup() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSGroup group = new EOSGroup().setName("Test Create")
				.setDescription("Create description")
				.setLevel(EOSLevel.PUBLIC.getLevel());
		group = svcGroup.createGroup(group);
		Assert.assertNotNull("Create: group Id not null", group.getId());
		Assert.assertNotNull("Create: tenant Id not null", group.getTenantId());
	}

	@Test
	public void testFindGroup() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSGroup group = createGroup("Find");
		EOSGroup find = svcGroup.findGroup(group.getId());
		Assert.assertEquals("Find Group", group.getId(), find.getId());
	}

	@Test
	public void testFindMultiple() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSGroup group1 = createGroup("Find Multiple 1");
		EOSGroup group2 = createGroup("Find Multiple 2");
		List<Long> ids = new ArrayList<>(2);

		ids.add(group1.getId());
		ids.add(group2.getId());

		List<EOSGroup> groups = svcGroup.findGroups(ids);
		Assert.assertEquals("Mult find size", 2, groups.size());
		Assert.assertEquals(group1.getId(), groups.get(0).getId());
		Assert.assertEquals(group2.getId(), groups.get(1).getId());
	}

	@Test
	public void testListGroups() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		createGroup("List 1");
		createGroup("List 2");
		List<EOSGroup> groups = svcGroup.listGroups(null, null, 50, 0);
		Assert.assertTrue("Size higher or equals than 2", groups.size() >= 2);
	}

	@Test
	public void testDeleteGroup() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException,
			EOSNotFoundException {
		EOSGroup group = createGroup("Delete");
		svcGroup.deleteGroup(group.getId());
		EOSGroup deleted = svcGroup.findGroup(group.getId());
		Assert.assertNull(deleted);

	}

	private EOSGroup createGroup(String identifier)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSGroup group = new EOSGroup().setName("Test " + identifier)
				.setDescription("Test description " + identifier)
				.setLevel(EOSLevel.PUBLIC.getLevel());

		return svcGroup.createGroup(group);
	}

}
