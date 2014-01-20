/**
 * 
 */
package com.eos.security.impl.service.internal;

/**
 * All known permissions for Security Module.
 * 
 * @author santos.fabiano
 * 
 */
public interface EOSKnownPermissions {

	/**
	 * Permission for access to do anything on anything.
	 */
	String PERMISSION_ALL = "EOS.Global.Permission.ALL";
	/**
	 * Prefix for global access permission for a tenant.
	 */
	String PERMISSION_TENAT_ALL = "EOS.Tenant.Permission.ALL-";
	String PASSWORD_UPDATE = "User.Upate.Password";
}
