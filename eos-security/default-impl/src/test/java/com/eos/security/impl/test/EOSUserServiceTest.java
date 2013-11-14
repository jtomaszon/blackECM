/**
 * 
 */
package com.eos.security.impl.test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.impl.service.EOSSystemConstants;
import com.eos.security.impl.session.SessionContextManager;

/**
 * Test class for user service.
 * 
 * @author santos.fabiano
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class EOSUserServiceTest {

	@Autowired
	private EOSUserService svcUser;
	@Autowired
	private EOSSecurityService svcSecurity;

	@Before
	public void setUp() {
		if (SessionContextManager.getCurrentSession() == null
				|| SessionContextManager.getCurrentSession().getTenant() == null) {
			svcSecurity.createSessionContext(UUID.randomUUID().toString(),
					EOSSystemConstants.ADMIN_TENANT);
		}
	}

	@Test
	public void testCreateUser() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSUser user = new EOSUser();
		user.setLogin("user_create").setEmail("user@create.com")
				.setFirstName("Create F").setLastName("Create L")
				.setNickName("Create N").setPersonalMail("create@personal.com");

		user = svcUser.createUser(user);
		Assert.assertEquals("Expected state after creation", EOSState.INACTIVE,
				user.getState());
	}

	@Test
	public void testFindUser() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSUser user = createUser("find");
		EOSUser find = svcUser.findUser(user.getLogin());
		Assert.assertNotNull("Find User", find);
		Assert.assertEquals("Find User", user.getLogin(), find.getLogin());
	}

	@Test
	public void testFindtenantUser() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSUser user = createUser("findtenant");
		EOSUser find = svcUser.findTenantUser(user.getLogin(), 99L);
		Assert.assertNull("Find Tenant User other tenant", find);
		// Now find for the current tenant
		find = svcUser.findTenantUser(user.getLogin(), user.getTenantId());
		Assert.assertNotNull("Find Tenant User current tenant", find);
		Assert.assertEquals("Find User", user.getLogin(), find.getLogin());

	}

	@Test
	public void testFindUsers() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSUser user1 = createUser("finds1");
		EOSUser user2 = createUser("finds2");

		List<EOSUser> users = svcUser.findUsers(Arrays.asList(user1.getLogin(),
				user2.getLogin()));
		Assert.assertEquals("Find users list size", 2, users.size());
		Assert.assertEquals("Find users User1", users.get(0).getLogin(),
				user1.getLogin());
		Assert.assertEquals("Find users User2", users.get(1).getLogin(),
				user2.getLogin());
	}

	public void testUpdateUser() throws EOSDuplicatedEntryException,
			EOSForbiddenException, EOSUnauthorizedException {
		EOSUser user = createUser("update");
		// Change names
		user.setFirstName("Updated F. Name").setLastName("Update L. Name");

		svcUser.updateUser(user);
	}

	private EOSUser createUser(String prefix)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSUser user = new EOSUser();
		user.setLogin(prefix + "_create").setEmail(prefix + "@create.com")
				.setFirstName(prefix + " First").setLastName(prefix + " Last")
				.setNickName(prefix + " Nick")
				.setPersonalMail(prefix + "@personal.com");

		user = svcUser.createUser(user);
		return user;
	}
}
