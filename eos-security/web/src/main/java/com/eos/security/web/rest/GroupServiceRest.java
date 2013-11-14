/**
 * 
 */
package com.eos.security.web.rest;

import java.util.Arrays;
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

import com.eos.security.api.service.EOSGroupService;
import com.eos.security.api.vo.EOSGroup;
import com.eos.security.api.vo.EOSRole;
import com.eos.security.api.vo.EOSUser;

/**
 * Group Rest Service.
 * 
 * @author santos.fabiano
 * 
 */
@Path("/group")
@Component
public class GroupServiceRest {

	private EOSGroupService svcGroup;

	private static final EOSGroup group;

	static {
		group = new EOSGroup();
		group.setId(1L);
		group.setName("Mock Group");
		group.setDescription("Mock Group");
		group.setLevel(5000);
		group.setTenantId(1L);
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSGroup createGroup(EOSGroup group,
			@Context final HttpServletResponse response) {
		response.setStatus(Response.Status.CREATED.getStatusCode());
		return this.group;
	}

	@Path("/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSGroup findGroup(@PathParam("id") Long groupId) {
		// TODO
		return group;
	}

	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateGroup(@PathParam("id") Long groupId, EOSGroup group) {
		// TODO
	}

	@Path("/{id}")
	@DELETE
	public void deleteGroup(@PathParam("id") Long groupId) {
		// TODO
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSGroup> listGroups(
			@QueryParam("minimumLevel") List<Integer> minimum,
			@QueryParam("maximumLevel") List<Integer> maximum,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		// TODO
		return Arrays.asList(group);
	}

	// Group User

	@Path("/{id}/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSUser> listUsers(@PathParam("id") Long groupId) {
		// TODO
		return Collections.emptyList();
	}

	@Path("/{id}/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUsers(@PathParam("id") Long groupId, List<String> users,
			@Context final HttpServletResponse response) {
		response.setStatus(Response.Status.CREATED.getStatusCode());
		// TODO
	}

	@Path("/{id}/user")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUsers(@PathParam("id") Long groupId, List<String> users) {
		// TODO
	}

	// Group Role

	@Path("/{id}/role")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSRole> listRole(@PathParam("id") Long groupId) {
		// TODO
		return Collections.emptyList();
	}

	@Path("/{id}/role")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRoles(@PathParam("id") Long groupId, List<Long> roles,
			@Context final HttpServletResponse response) {
		response.setStatus(Response.Status.CREATED.getStatusCode());
		// TODO
	}

	@Path("/{id}/role")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeRoles(@PathParam("id") Long groupId, List<Long> roles) {
		// TODO
	}

}
