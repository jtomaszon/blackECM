/**
 * 
 */
package com.eos.security.impl.service.internal;

import java.util.regex.Pattern;

import com.eos.common.exception.EOSError;
import com.eos.common.exception.EOSErrorCodes;
import com.eos.common.util.StringUtil;
import com.eos.commons.jpa.EntityFieldSizes;

/**
 * Validation utility.
 * 
 * @author santos.fabiano
 * 
 */
public final class ValidationUtils {

	private static String getMessage(String validated, String expected,
			Object received) {
		return "Expected values for [" + validated + "] are [" + expected
				+ "] but [" + String.valueOf(received) + "] was received";
	}

	static public EOSError maxLength(String fieldName, String value, int max) {
		if (value != null && value.length() > max) {
			String message = getMessage(fieldName,
					fieldName + " > " + String.valueOf(max),
					String.valueOf(value.length()));

			return new EOSError(EOSErrorCodes.MAX_SIZE, message);
		}
		return null;
	}

	static public EOSError requiredNotNull(String fieldName, Object value) {
		if (value == null) {
			String message = getMessage(fieldName, "not null object", value);
			return new EOSError(EOSErrorCodes.REQUIRED, message);
		}
		return null;
	}

	static public EOSError requiredBoolean(String fieldName, Boolean value) {
		return requiredNotNull(fieldName, value);
	}

	static public EOSError validateString(String fieldName, String value,
			boolean required, int min, int max) {
		String message = getMessage(fieldName, String.valueOf(min) + " <= "
				+ fieldName + " <= " + String.valueOf(max),
				value != null ? String.valueOf(value.length()) : "null");

		if (required && StringUtil.isEmpty(value)) {
			return new EOSError(EOSErrorCodes.REQUIRED, message);
		}

		if (value != null && (value.length() < min || value.length() > max)) {
			int code = 0;

			if (value.length() < min) {
				code = EOSErrorCodes.MIN_SIZE;
			} else {
				code = EOSErrorCodes.MAX_SIZE;
			}

			return new EOSError(code, message);
		}

		return null;
	}

	static public EOSError requiredNumber(String fieldName, Number value,
			int min, int max) {
		EOSError error = requiredNotNull(fieldName, value);

		if (error == null && value.doubleValue() < min
				|| value.doubleValue() > max) {
			int code = 0;
			String message = getMessage(
					String.valueOf(value),
					String.valueOf(min) + " <= " + value + " <= "
							+ String.valueOf(max), String.valueOf(value));

			if (value.doubleValue() < min)
				code = EOSErrorCodes.MIN_SIZE;
			else
				code = EOSErrorCodes.MAX_SIZE;

			error = new EOSError(code, message);
		}

		return error;
	}

	private static final Pattern EMAIL_PATTERN = Pattern
			.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]+");
	private static final String EMAIL_ERR = "EMAIL Address";

	public static EOSError validateEmail(String fieldName, String email,
			boolean required) {
		EOSError error = validateString(fieldName, email, required,
				EntityFieldSizes.EMAIL_MIN_SIZE, EntityFieldSizes.DATA_LARGE);

		if (error != null)
			return error;

		if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
			String message = getMessage(fieldName, EMAIL_ERR, email);
			return new EOSError(EOSErrorCodes.INVALID_EMAIL, message);
		}

		return null;
	}

	public static EOSError validatePassword(String fieldName, String password,
			boolean required) {
		EOSError error = validateString(fieldName, password, required,
				EntityFieldSizes.PASSWORD_MIN_SIZE, EntityFieldSizes.DATA_TINY);
		// TODO validate password strength

		return error;
	}

	private static final Pattern PATH_PATTERN = Pattern
			.compile("[a-zA-Z0-9]+[a-zA-Z0-9.-]+");
	private static final String LOGIN_ERROR = "Invalid characters";

	public static EOSError requiredLogin(String fieldName, String login) {
		EOSError error = validateString(fieldName, login, true, 1,
				EntityFieldSizes.DATA_TINY);

		if (error != null)
			return error;

		if (!PATH_PATTERN.matcher(login).matches()) {
			String message = getMessage(fieldName, LOGIN_ERROR, login);
			error = new EOSError(EOSErrorCodes.INVALID_LOGIN, message);
		}

		return error;
	}

	public static EOSError validatePermission(String fieldName,
			String permission) {
		EOSError error = validateString(fieldName, permission, true,
				EntityFieldSizes.MINIMUM, EntityFieldSizes.DATA_TINY);

		if (error != null) {
			return error;
		}

		if (!PATH_PATTERN.matcher(permission).matches()) {
			String message = getMessage(fieldName, LOGIN_ERROR, permission);
			error = new EOSError(EOSErrorCodes.INVALID_LOGIN, message);
		}

		return error;
	}

}
