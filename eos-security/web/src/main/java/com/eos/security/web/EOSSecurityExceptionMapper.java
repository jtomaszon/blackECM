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
import com.eos.security.api.exception.EOSForbiddenException;
import com.eos.security.api.exception.EOSUnauthorizedException;
import com.eos.security.web.dto.EOSExceptionData;

/**
 * Mapper to handle exceptions.
 * 
 * @author santos.fabiano
 * 
 */
@Provider
public class EOSSecurityExceptionMapper implements ExceptionMapper<Exception> {

	private static final Logger log = LoggerFactory
			.getLogger(EOSSecurityExceptionMapper.class);

	/**
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(Exception exception) {
		log.error("Returning exception as JSON.", exception);
		ResponseBuilder builder = null;
		EOSException eosException = null;

		if (EOSNotFoundException.class.isAssignableFrom(exception.getClass())) {
			builder = Response.status(HttpServletResponse.SC_NOT_FOUND);
			eosException = (EOSException) exception;
		} else if (EOSForbiddenException.class.isAssignableFrom(exception
				.getClass())) {
			builder = Response.status(HttpServletResponse.SC_FORBIDDEN);
			eosException = (EOSException) exception;
		} else if (EOSUnauthorizedException.class.isAssignableFrom(exception
				.getClass())) {
			builder = Response.status(HttpServletResponse.SC_UNAUTHORIZED);
			eosException = (EOSException) exception;
		} else if (EOSException.class.isAssignableFrom(exception.getClass())) {
			builder = Response.status(HttpServletResponse.SC_BAD_REQUEST);
			eosException = (EOSException) exception;
		} else {
			builder = Response
					.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			eosException = new EOSException(exception.getMessage());
		}

		builder.type(MediaType.APPLICATION_JSON).entity(
				formatResponse(eosException));
		return builder.build();
	}

	/**
	 * Extract errors from an exception.
	 * 
	 * @param exception
	 *            Exception to extract errors.
	 * @return Return exception data errors object.
	 */
	private static EOSExceptionData formatResponse(EOSException exception) {
		return new EOSExceptionData(exception.getMessage(),
				exception.getErrors());
	}

}
