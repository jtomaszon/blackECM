/**
 * 
 */
package com.eos.security.web.rest;

import java.util.Collections;
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

import com.eos.security.api.service.EOSRoleService;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;

/**
 * @author santos.fabiano
 * 
 */
@Path("/role")
@Component
public class RoleServiceRest {

	private EOSRoleService svcRole;

	private static final EOSRole role;

	static {
		role = new EOSRole();
		role.setCode("Mock_Role");
		role.setDescription("Mock Role");
		role.setLevel(5000);
		role.setTenantId(1L);
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSRole createRole(EOSRole role,
			@Context final HttpServletResponse response) {
		// TODO
		response.setStatus(Response.Status.CREATED.getStatusCode());
		return this.role;
	}

	@Path("/{code}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateRole(@PathParam("code") String code, EOSRole role) {

	}

	@Path("/{code}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeRole(@PathParam("code") String code) {

	}

	@Path("/{code}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public void findRole(@PathParam("code") String code) {

	}

	@Path("/list")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<EOSRole> listRoles(
			@QueryParam("minimumLevel") List<Integer> minimum,
			@QueryParam("maximumLevel") List<Integer> maximum,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		// TODO
		return Collections.emptyList();
	}

	// Role user

	@Path("{code}/user")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<EOSUser> listUsers(@PathParam("code") String code,
			List<String> users) {
		// TODO
		return Collections.emptyList();
	}

	@Path("{code}/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUsers(@PathParam("code") String code, List<String> users,
			@Context final HttpServletResponse response) {
		// TODO
		response.setStatus(Response.Status.CREATED.getStatusCode());
	}

	@Path("{code}/user")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUsers(@PathParam("code") String code, List<String> users) {
		// TODO
	}

	// Group

	@Path("{code}/group")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<EOSUser> listGroups(@PathParam("code") String code,
			List<Long> groups) {
		// TODO
		return Collections.emptyList();
	}

	@Path("{code}/group")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addGroups(@PathParam("code") String code, List<Long> groups,
			@Context final HttpServletResponse response) {
		// TODO
		response.setStatus(Response.Status.CREATED.getStatusCode());
	}

	@Path("{code}/group")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeGroups(@PathParam("code") String code, List<Long> groups) {
		// TODO
	}

	// Permissions

	@Path("{code}/permission")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public List<EOSUser> listPermissions(@PathParam("code") String code,
			List<String> permissions) {
		// TODO
		return Collections.emptyList();
	}

	@Path("{code}/permission")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPermissions(@PathParam("code") String code,
			List<String> permissions,
			@Context final HttpServletResponse response) {
		// TODO
		response.setStatus(Response.Status.CREATED.getStatusCode());
	}

	@Path("{code}/permission")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removePermissions(@PathParam("code") String code,
			List<String> permissions) {
		// TODO
	}

}
