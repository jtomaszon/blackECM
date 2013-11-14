/**
 * 
 */
package com.eos.security.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.eos.common.EOSState;
import com.eos.security.api.service.EOSSecurityService;
import com.eos.security.api.session.SessionContext;
import com.eos.security.api.vo.EOSTenant;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.web.dto.EOSCredentials;
import com.eos.security.web.dto.EOSPermissionData;

/**
 * @author santos.fabiano
 * 
 */
@Path("/access")
@Component
public class SecurityServiceRest {

	@Context
	private HttpServletRequest request;

	private EOSSecurityService svcSecurity;

	private static final SessionContext context;

	static {
		EOSTenant tenant = new EOSTenant();
		tenant.setId(1L);
		tenant.setName("Mock Tenant");
		tenant.setDescription("Mock Tenant");
		tenant.setState(EOSState.ACTIVE);

		EOSUser user = new EOSUser();
		user.setLogin("mock_user");
		user.setFirstName("First");
		user.setLastName("Last");
		user.setNickName("Nick");
		user.setPersonalMail("personal@mail.com");
		user.setEmail("mock@usermail.com");
		user.setUrl("/security/user/mock_user");
		user.setTenantId(1L);
		user.setState(EOSState.ACTIVE);

		context = new SessionContext(tenant, user);
	}

	@Path("/login")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void login(EOSCredentials credentials) {
		// TODO
	}

	@Path("/logout")
	@PUT
	public void logout() {
		// TODO
	}

	@Path("/context")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SessionContext getSessionContext() {
		// TODO
		return context;
	}

	@Path("/{login}/permission")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSPermissionData hasPermission(@PathParam("login") String login,
			String permission) {
		// TODO
		return new EOSPermissionData().setLogin(login)
				.setHasPermission(Boolean.TRUE).setPermission(permission);
	}
}
