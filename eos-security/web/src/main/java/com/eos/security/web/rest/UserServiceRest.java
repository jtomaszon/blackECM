/**
 * 
 */
package com.eos.security.web.rest;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.eos.common.EOSState;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSUser;

/**
 * @author santos.fabiano
 * 
 */
@Path("/user")
@Component
public class UserServiceRest {

	private EOSUserService svcUser;

	private static final EOSUser user;

	static {
		user = new EOSUser();
		user.setLogin("mock_user");
		user.setFirstName("First");
		user.setLastName("Last");
		user.setNickName("Nick");
		user.setPersonalMail("personal@mail.com");
		user.setEmail("mock@usermail.com");
		user.setUrl("/security/user/mock_user");
		user.setTenantId(1L);
		user.setState(EOSState.ACTIVE);
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSUser createUser(EOSUser user,
			@Context final HttpServletResponse response) {
		response.setStatus(Response.Status.CREATED.getStatusCode());
		return this.user;
	}

	@Path("/{login}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(@PathParam("login") String login, EOSUser user) {
		// TODO
	}

	@Path("/{login}")
	@DELETE
	public void deleteUser(@PathParam("login") String login) {
		// TODO
	}

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EOSUser findUser(@PathParam("login") String login) {
		return user;
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSUser> listUsers(
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		return Arrays.asList(user);
	}
}
