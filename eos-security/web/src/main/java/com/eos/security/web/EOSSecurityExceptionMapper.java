/**
 * 
 */
package com.eos.security.web;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eos.common.exception.EOSException;
import com.eos.common.exception.EOSNotFoundException;
import com.eos.common.exception.EOSRuntimeException;
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.web.util.WebUtils;

/**
 * Mapper to handle exceptions.
 * 
 * @author santos.fabiano
 * 
 */
@Provider
public class EOSSecurityExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger log = LoggerFactory
			.getLogger(EOSSecurityExceptionMapper.class);

	/**
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(Throwable throwable) {
		log.error("Returning exception as JSON.", throwable);
		ResponseBuilder builder = null;
		EOSException exception = null;

		if (EOSNotFoundException.class.isAssignableFrom(throwable.getClass())) {
			builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
			exception = (EOSException) throwable;
		} else if (EOSForbiddenException.class.isAssignableFrom(throwable
				.getClass())) {
			builder = Response.status(HttpServletResponse.SC_FORBIDDEN);
			exception = (EOSException) throwable;
		} else if (EOSUnauthorizedException.class.isAssignableFrom(throwable
				.getClass())) {
			builder = Response.status(HttpServletResponse.SC_UNAUTHORIZED);
			exception = (EOSException) throwable;
		} else if (EOSException.class.isAssignableFrom(throwable.getClass())) {
			builder = Response.status(HttpServletResponse.SC_BAD_REQUEST);
			exception = (EOSException) throwable;
		} else if (EOSRuntimeException.class.isAssignableFrom(throwable
				.getClass())) {
			builder = Response
					.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			EOSRuntimeException runtime = (EOSRuntimeException) throwable;
			exception = new EOSException(runtime.getMessage(),
					runtime.getErrors());
		} else {
			builder = Response
					.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			exception = new EOSException(throwable.getMessage());
		}

		builder.type(MediaType.APPLICATION_JSON).entity(
				WebUtils.formatResponse(exception));
		return builder.build();
	}

}
