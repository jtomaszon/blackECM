/**
 * 
 */
package com.eos.security.impl.service;

/**
 * System constants.
 * 
 * @author santos.fabiano
 * 
 */
public interface EOSSystemConstants {

	/**
	 * Global tenant ID.
	 */
	Long ADMIN_TENANT = 1L;
	/**
	 * Super Administrator user login.
	 */
	String LOGIN_SUPER_ADMIN = "eosadmin";
	/**
	 * Anonymous user login.
	 */
	String LOGIN_ANONYMOUS = "anonymous";
	/**
	 * Security task user login.
	 */
	String LOGIN_SYSTEM_USER = "security.user";
	/**
	 * Super Administrator role code.
	 */
	String ROLE_SUPER_ADMIN = "EOSAdmin";
	/**
	 * Anonymous role code.
	 */
	String ROLE_ANONYMOUS = "EOSAdmin";
	/**
	 * Tenant administrator.
	 */
	String ROLE_TENANT_ADMIN = "Admin";
	/**
	 * Administrative internal level.
	 */
	Integer INTERNAL_LEVEL = 1;
	/**
	 * Permission for access to do anything on anything.
	 */
	String PERMISSION_ALL = "EOS.Permission.ALL";
}
