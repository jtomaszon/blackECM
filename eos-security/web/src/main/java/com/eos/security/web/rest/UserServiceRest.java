/**
 * 
 */
package com.eos.security.web.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSUserService;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;
import com.eos.security.web.dto.EOSUserCreateData;

/**
 * @author santos.fabiano
 * 
 */
@Path("/user")
@Component
public class UserServiceRest {

	private EOSUserService svcUser;

	// User

	@Path("/{login}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EOSUser findUser(@PathParam("login") String login)
			throws EOSNotFoundException {
		return svcUser.findUser(login);
	}

	@Path("/{login}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(@PathParam("login") String login, EOSUser user)
			throws EOSForbiddenException, EOSUnauthorizedException,
			EOSNotFoundException {
		svcUser.updateUser(user);
	}

	@Path("/{login}")
	@DELETE
	public void deleteUser(@PathParam("login") String login)
			throws EOSForbiddenException, EOSUnauthorizedException {
		svcUser.deleteUser(login);
	}

	@Path("")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSUser createUser(EOSUserCreateData user,
			@Context final HttpServletResponse response)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {
		EOSUser ret = svcUser.createUser(user.getUser(), user.getUserData());
		response.setStatus(Response.Status.CREATED.getStatusCode());
		return ret;
	}

	@Path("/find")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSUser> findUsers(@QueryParam("login") List<String> logins) {
		return svcUser.findUsers(logins);
	}

	@Path("")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSUser> listUsers(@QueryParam("state") List<EOSState> states,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		return svcUser.listUsers(states, limit, offset);
	}

	// User Permissions

	@Path("/{login}/permission")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Boolean> hasPermissions(
			@PathParam("login") String login,
			@QueryParam("permission") List<String> permissions) {
		// TODO
		return Collections.emptyMap();
	}

	// User Groups

	@Path("/{login}/groups")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSGroup> listUserGroups(@PathParam("login") String login,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		// TODO
		return Collections.emptyList();
	}

	// User Roles

	@Path("/{login}/roles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSRole> listUserRoles(@PathParam("login") String login,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		// TODO
		return Collections.emptyList();
	}

	// User Data

	@Path("/{login}/data")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUserData(@PathParam("login") String login,
			Map<String, String> userData) throws EOSForbiddenException,
			EOSUnauthorizedException {
		svcUser.updateUserData(login, userData);
	}

	@Path("/{login}/data")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> listUserData(@PathParam("login") String login,
			List<String> keys) {
		return svcUser.listUserData(login, keys);
	}

	@Path("/{login}/data")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> listAllUserData(
			@PathParam("login") String login,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		return svcUser.listUserData(login, limit, offset);
	}

}
