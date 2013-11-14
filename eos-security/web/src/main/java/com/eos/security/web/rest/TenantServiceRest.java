/**
 * 
 */
package com.eos.security.web.rest;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eos.common.EOSState;
import com.eos.common.exception.EOSDuplicatedEntryException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.api.service.EOSTenantService;
import com.eos.security.api.vo.EOSTenant;

/**
 * Tenant rest services.
 * 
 * @author santos.fabiano
 * 
 */
@Path("/tenant")
@Component
public class TenantServiceRest {

	private static EOSTenantService svcTenant;

	@Context
	private HttpServletResponse response;

	@Autowired
	private void setTenantService(EOSTenantService eosTenantService) {
		svcTenant = eosTenantService;
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EOSTenant createTenant(EOSTenant tenant)
			throws EOSDuplicatedEntryException, EOSForbiddenException,
			EOSUnauthorizedException {

		tenant = svcTenant.createTenant(tenant);
		response.setStatus(Response.Status.CREATED.getStatusCode());
		return tenant;
	}

	@Path("/{tenantId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EOSTenant findTenant(@PathParam("tenant") Long tenantId)
			throws EOSNotFoundException {
		EOSTenant tenant = svcTenant.findTenant(tenantId);

		if (tenant == null) {
			throw new EOSNotFoundException("Tenant not found");
		}

		return tenant;
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EOSTenant> listTenants(
			@QueryParam("state") List<EOSState> states,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("offset") @DefaultValue("0") int offset) {
		return svcTenant.listTenants(states, limit, offset);
	}

	@Path("/{tenantId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTenant(@PathParam("tenant") Long tenantId,
			EOSTenant tenant) throws EOSForbiddenException,
			EOSUnauthorizedException {
		tenant.setId(tenantId);
		svcTenant.updateTenant(tenant);
	}

	@Path("/{tenantId}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteTenant(@PathParam("tenant") Long tenantId)
			throws EOSForbiddenException, EOSUnauthorizedException {
		svcTenant.updateTenantState(tenantId, EOSState.REMOVED);
	}
}
